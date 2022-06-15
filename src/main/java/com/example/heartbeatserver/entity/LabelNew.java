package com.example.heartbeatserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName label_new
 */
@TableName(value ="label_new")
@Data
public class LabelNew implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer labelid;

    /**
     * 标签等级
     */
    private Integer labellevel;

    /**
     * 
     */
    private String labelname;

    /**
     * 
     */
    private Integer labelrank;

    /**
     * 
     */
    private Date createtime;

    /**
     * adminID
     */
    private Integer createuser;

    /**
     * 0 - 未删除, 1 - 已删除
     */
    private Integer isdeleted;

    /**
     * 一级分类默认为0
     */
    private Integer parentid;

    /**
     * 
     */
    private Date updatetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        LabelNew other = (LabelNew) that;
        return (this.getLabelid() == null ? other.getLabelid() == null : this.getLabelid().equals(other.getLabelid()))
            && (this.getLabellevel() == null ? other.getLabellevel() == null : this.getLabellevel().equals(other.getLabellevel()))
            && (this.getLabelname() == null ? other.getLabelname() == null : this.getLabelname().equals(other.getLabelname()))
            && (this.getLabelrank() == null ? other.getLabelrank() == null : this.getLabelrank().equals(other.getLabelrank()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getCreateuser() == null ? other.getCreateuser() == null : this.getCreateuser().equals(other.getCreateuser()))
            && (this.getIsdeleted() == null ? other.getIsdeleted() == null : this.getIsdeleted().equals(other.getIsdeleted()))
            && (this.getParentid() == null ? other.getParentid() == null : this.getParentid().equals(other.getParentid()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLabelid() == null) ? 0 : getLabelid().hashCode());
        result = prime * result + ((getLabellevel() == null) ? 0 : getLabellevel().hashCode());
        result = prime * result + ((getLabelname() == null) ? 0 : getLabelname().hashCode());
        result = prime * result + ((getLabelrank() == null) ? 0 : getLabelrank().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getCreateuser() == null) ? 0 : getCreateuser().hashCode());
        result = prime * result + ((getIsdeleted() == null) ? 0 : getIsdeleted().hashCode());
        result = prime * result + ((getParentid() == null) ? 0 : getParentid().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", labelid=").append(labelid);
        sb.append(", labellevel=").append(labellevel);
        sb.append(", labelname=").append(labelname);
        sb.append(", labelrank=").append(labelrank);
        sb.append(", createtime=").append(createtime);
        sb.append(", createuser=").append(createuser);
        sb.append(", isdeleted=").append(isdeleted);
        sb.append(", parentid=").append(parentid);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}