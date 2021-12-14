package org.xdl.crawler.crawlers;

import org.springframework.web.client.RestTemplate;
import org.xdl.crawler.Constants.WebSiteConstants;
import org.xdl.crawler.data.BaseItem;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author XieDuoLiang
 * @date 2021/12/14 下午1:15
 */
public abstract class BaseCrawler {

    private String url;

    protected Integer page;

    protected Integer pageSize;

    public static BaseCrawler getInstance(String webSite) {
        if (WebSiteConstants.SSE.equals(webSite)) {
            return new SSECrawler();
        }
        return null;
    }

    public List<? extends BaseItem> getList() {
        return new ArrayList<>();
    }

    /**
     * 获取指定更新日期的数据
     * @param date 格式：yyyy-MM-dd
     * @param total //指定更新日期的总数据
     * */
    public void getList(String date, List<BaseItem> total) {
        List<? extends BaseItem> items = getList();
        for (BaseItem item:items) {
            if (date.equals(item.getUpdateTime())) {
                total.add(item);
            }
        }
        if (items.get(items.size() - 1).getUpdateTime().compareTo(date) >= 0) {
            setPage(getPage() + 1);
            getList(date,total);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPage() {
        if (page == null)
            return 1;
        return page;
    }

    public void setPage(Integer page) {
        if (page == null)
            this.page = 1;
        this.page = page;
    }

    public Integer getPageSize() {
        if (pageSize == null)
            return 20;
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize > 20)
            this.pageSize = 20;
        this.pageSize = pageSize;
    }
}
