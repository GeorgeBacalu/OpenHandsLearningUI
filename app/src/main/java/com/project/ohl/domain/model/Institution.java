package com.project.ohl.domain.model;

import java.util.UUID;
public class Institution {

   private UUID id;
   private String school;
   private String classroom;

   public Institution() {
   }

   public Institution(UUID id, String school, String classroom) {
      this.id = id;
      this.school = school;
      this.classroom = classroom;
   }

   public UUID getId() {
      return id;
   }

   public void setId(UUID id) {
      this.id = id;
   }

   public String getSchool() {
      return school;
   }

   public void setSchool(String school) {
      this.school = school;
   }

   public String getClassroom() {
      return classroom;
   }

   public void setClassroom(String classroom) {
      this.classroom = classroom;
   }

   @Override
   public String toString() {
      return "Institution{" + "id=" + id + ", school='" + school + '\'' + ", classroom='" + classroom + '\'' + '}';
   }
}
