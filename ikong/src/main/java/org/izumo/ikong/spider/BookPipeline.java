package org.izumo.ikong.spider;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

public class BookPipeline extends ConsolePipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        /*
                for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                    if ("meituanPrice".equals(entry.getKey())) {
                        spiderBean.setMeituanPrice(Double.parseDouble(entry.getValue().toString()));
                    }
                    if ("meituanCount".equals(entry.getKey())) {
                        spiderBean.setMeituanCount(Integer.parseInt(entry.getValue().toString()));
                    }
                    if ("nuomiPrice".equals(entry.getKey())) {
                        spiderBean.setNuomiPrice(Double.parseDouble(entry.getValue().toString()));
                    }
                    if ("nuomiCount".equals(entry.getKey())) {
                        spiderBean.setNuomiCount(Integer.parseInt(entry.getValue().toString()));
                    }
                }*/
    }
}
