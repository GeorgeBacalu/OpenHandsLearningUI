package com.example.llt_project_separate.general.standard_classes;

import com.example.llt_project_separate.general.standard_classes.enums.Authority;

public class Role {
   private Long id;
   private Authority authority;

   public Role() {
   }

   public Role(Long id, Authority authority) {
      this.id = id;
      this.authority = authority;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Authority getAuthority() {
      return authority;
   }

   public void setAuthority(Authority authority) {
      this.authority = authority;
   }

   @Override
   public String toString() {
      return "Role{" + "id=" + id + ", authority=" + authority + '}';
   }
}
