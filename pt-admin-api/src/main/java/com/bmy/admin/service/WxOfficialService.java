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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName WxOfficialService
 * @Description 微信公众号认证及文章获取
 * @Author potato
 * @Date 2020/12/24 下午3:10
 **/
@Service
public class  WxOfficialService {

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
            return this.getOfficialNews(count);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 带上access_token请求公众号接口
     * 获取count数量的news
     * @param count
     * @return
     */
    @Transactional
    public int getOfficialNews(Integer count){
        String param = "{\"type\": \"news\" ,\"offset\" : 1 ,\"count\": "+ count +"}";
        String url = String.format(wxOfficialProperties.getApi().get("get_news"),this.getWxOfficialToken().getAccess_token());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(param,headers);
        String response = restTemplate.postForObject(url,entity,String.class);
        int integer = 0;//更新成功条数
        if (null != response){
            response = new String(response.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            JSONObject jsonObject = gson.fromJson(response,JSONObject.class);
            JSONArray jsonArray = jsonObject.getJSONArray("item");

            for (Object object : jsonArray) {
                try {
                    JSONObject json = (JSONObject) object;
                    String mediaId = json.getString("media_id");
                    logger.info(mediaId);
                    json = json.getJSONObject("content");
                    Date updateTime = json.getDate("update_time");

                    JSONArray arr = json.getJSONArray("news_item");
                    json = (JSONObject) arr.get(0);
                    WxOfficialNews wxOfficialNews = WxOfficialNews.builder()
                            .title(json.getString("title"))
                            .updateTime(updateTime)
                            .url(json.getString("url"))
                            .mediaId(mediaId)
                            .thumbUrl(json.getString("thumb_url"))
                            .build();
                    if (wxOfficialNewsMapper.insert(wxOfficialNews) == 1){
                        integer ++;
                    }
                }catch (DuplicateKeyException e){
                    logger.info("该新闻已存在");
                }
            }
        }
        return integer;
    }


}
