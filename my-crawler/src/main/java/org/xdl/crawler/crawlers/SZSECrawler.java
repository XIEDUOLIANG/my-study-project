package org.xdl.crawler.crawlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.xdl.crawler.data.BaseItem;
import org.xdl.crawler.data.SZSEKCProjectDynamicItem;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SZSECrawler extends BaseCrawler{

    public SZSECrawler() {
        this.page = 0;
        this.pageSize = 50;
        this.setUrl("http://listing.szse.cn/api/ras/projectrends/query?");
    }

    //region 获取项目动态列表
    @Override
    public List<? extends BaseItem> getList() {
        String url = this.getUrl() +
                "bizType=" + "1" +
                "&" +
                "random=" + Math.random() +
                "&" +
                "pageIndex=" + this.getPage() +
                "&" +
                "pageSize=" + this.getPageSize();
        RestTemplate restTemplate = new RestTemplate();
        log.info("请求url：{}",url);
        String result = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSONObject.parseObject(result, JSONObject.class);
        Object data = jsonObject.get("data");
        List<JSONObject> jsonObjects = JSONObject.parseArray(JSON.toJSONString(data), JSONObject.class);
        return jsonObjects.stream().map(x -> {
            SZSEKCProjectDynamicItem item = new SZSEKCProjectDynamicItem();
            item.setCmpnm((String) x.get("cmpnm"));
            item.setUpdateTime((String) x.get("updtdt"));
            return item;
        }).collect(Collectors.toList());
    }
}
