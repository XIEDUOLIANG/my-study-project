package org.xdl.crawler.crawlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.xdl.crawler.data.BaseItem;
import org.xdl.crawler.data.SSEKCProjectDynamicItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 上海证券交易所-科创板项目动态
 * */
@Slf4j
public class SSECrawler extends BaseCrawler{

    public SSECrawler() {
        this.page = 1;
        this.pageSize = 50;
        this.setUrl("https://query.sse.com.cn/statusAction.do?");
    }

    //region 获取项目动态列表
    @Override
    public List<? extends BaseItem> getList() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Referer","https://kcb.sse.com.cn/");
        HttpEntity<String> entity = new HttpEntity<>("parameters",headers);
        String url = this.getUrl() + "jsonCallBack=" + "jsonpCallback7174" +
                "&" +
                "isPagination=" + "true" +
                "&" +
                "sqlId=" + "SH_XM_LB" +
                "&" +
                "pageHelp.pageSize=" + this.getPageSize() +
                "&" +
                "order=" + "updateDate|desc" + //更新时间倒序排序
                "&" +
                "pageHelp.pageNo=" + this.getPage() +
                "&" +
                "pageHelp.beginPage=" + this.getPage() +
                "&" +
                "pageHelp.endPage=" + this.getPage() +
                "&" +
                "_=" + "1639448904018";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        log.info("sse请求url：{}",url);
        if (result.getStatusCode().equals(HttpStatus.OK)) {
            String body = result.getBody();
            if (body == null)
                return new ArrayList<>();
            String content = body.replaceFirst("jsonpCallback7174\\(","").replaceAll("\\)","");
            JSONObject jsonObject = JSONObject.parseObject(content, JSONObject.class);
            Object pageHelp = jsonObject.get("pageHelp");
            jsonObject = JSONObject.parseObject(JSON.toJSONString(pageHelp));
            List<JSONObject> jsonObjects = JSONObject.parseArray(JSON.toJSONString(jsonObject.get("data")), JSONObject.class);
            return jsonObjects.stream().map(x -> {
                SSEKCProjectDynamicItem item = new SSEKCProjectDynamicItem();
                item.setStockAuditName((String) x.get("stockAuditName"));
                String updateTime = (String) x.get("updateDate");
                try {
                    Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(updateTime);
                    item.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd").format(date));
                } catch (ParseException e) {
                    log.error("接口返回时间格式异常：{}",updateTime);
                    e.printStackTrace();
                }
                return item;
            }).collect(Collectors.toList());
            //todo 返回数据 正则匹配不上
        }
        return null;
    }
    //endregion
}
