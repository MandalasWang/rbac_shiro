package ink.boyuan.rbac_shiro.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author wyy
 */
public class Role implements Serializable {

  private Integer id;
  private String roleName;
  private String remark;

  private List<Permission> permissionList;

  public List<Permission> getPermissionList() {
    return permissionList;
  }

  public void setPermissionList(List<Permission> permissionList) {
    this.permissionList = permissionList;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
