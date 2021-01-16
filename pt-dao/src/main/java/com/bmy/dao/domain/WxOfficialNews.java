package com.bmy.dao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName WxOfficialNews
 * @Description 微信公众号news
 * @Author potato
 * @Date 2020/12/24 下午3:47
 **/
@Data
@Builder
@Table(name = "official_news")
@AllArgsConstructor
@NoArgsConstructor
public class WxOfficialNews implements Serializable {

    @Id
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer id;
    @Column
    private String mediaId;//媒体id唯一标识
    @Column
    private String title;//图文消息的标题
    @Column
    private String thumbUrl;//缩略图
    @Column
    private String url;//图文页的URL，或者，当获取的列表是图片素材列表时，该字段是图片的URL
    @Column
    private Date updateTime;

    @Column
    private String digest;

}
