package uw.gateway.center.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import uw.gateway.center.entity.MscAclFilter;
import uw.gateway.center.entity.MscAclFilterData;

import java.util.List;

/**
 * mscIP访问控制扩展数据。
 */

@Schema(title = "IP访问控制扩展数据", description = "IP访问控制扩展数据")
public class MscAclFilterEx extends MscAclFilter {

    /**
     * 数据列表。
     */
    private List<MscAclFilterData> dataList;

    public List<MscAclFilterData> getDataList() {
        return dataList;
    }

    public void setDataList(List<MscAclFilterData> dataList) {
        this.dataList = dataList;
    }
}
