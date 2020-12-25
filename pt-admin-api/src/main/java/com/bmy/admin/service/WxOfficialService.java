package com.bmy.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bmy.admin.config.WxOfficialProperties;
import com.bmy.dao.domain.WxOfficialNews;
import com.bmy.admin.vo.WxOfficialToken;
import com.bmy.dao.mapper.WxOfficialNewsMapper;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName WxOfficialService
 * @Description 微信公众号认证及文章获取
 * @Author potato
 * @Date 2020/12/24 下午3:10
 **/
@Service
public class WxOfficialService {

    Logger logger = LoggerFactory.getLogger(WxOfficialService.class);

    @Resource
    private WxOfficialProperties wxOfficialProperties;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private Gson gson;

    @Resource
    private WxOfficialNewsMapper wxOfficialNewsMapper;

    /**
     * 获取微信公众号接口access_token
     * @return
     */
    public WxOfficialToken getWxOfficialToken(){
        String url = String.format(wxOfficialProperties.getApi().get("auth"),wxOfficialProperties.getAppId(),wxOfficialProperties.getAppSecret());
        String res = restTemplate.getForObject(url,String.class);
        return gson.fromJson(res,WxOfficialToken.class);
    }

    /**
     * 清空新闻表，而后更新新闻数据
     * @param count
     * @return
     */
    public int refresh(Integer count){
        try {
            int res = wxOfficialNewsMapper.truncateTable();
            if ( 0 == res){
                 this.getOfficialNews(count);
                 return 1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 带上access_token请求公众号接口
     * 获取count数量的news
     * @param count
     * @return
     */
    @Transactional
    public List<WxOfficialNews> getOfficialNews(Integer count){
        String param = "{\"type\": \"news\" ,\"offset\" : 1 ,\"count\": "+ count +"}";
        String url = String.format(wxOfficialProperties.getApi().get("get_news"),this.getWxOfficialToken().getAccess_token());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(param,headers);
        String response = restTemplate.postForObject(url,entity,String.class);
        List<WxOfficialNews> list = new ArrayList<>();
        if (null != response){
            try {
                response = new String(response.getBytes("ISO-8859-1"),"UTF-8");
                JSONObject jsonObject = gson.fromJson(response,JSONObject.class);
                JSONArray jsonArray = jsonObject.getJSONArray("item");
                for (Object object : jsonArray) {
                    JSONObject json = (JSONObject) object;
                    json = json.getJSONObject("content");
                    JSONArray arr = json.getJSONArray("news_item");
                    json = (JSONObject) arr.get(0);
                    WxOfficialNews wxOfficialNews = WxOfficialNews.builder()
                            .title(json.getString("title"))
                            .url(json.getString("url"))
                            .thumbUrl(json.getString("thumb_url"))
                            .build();
                    list.add(wxOfficialNews);
                    wxOfficialNewsMapper.insert(wxOfficialNews);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


}
