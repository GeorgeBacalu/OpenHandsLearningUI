package com.project.ohl.domain.model;

import java.util.UUID;
import com.project.ohl.domain.model.enums.Authority;

public class Role {
   private UUID id;
   private Authority authority;

   public Role() {
   }

   public Role(UUID id, Authority authority) {
      this.id = id;
      this.authority = authority;
   }

   public UUID getId() {
      return id;
   }

   public void setId(UUID id) {
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
