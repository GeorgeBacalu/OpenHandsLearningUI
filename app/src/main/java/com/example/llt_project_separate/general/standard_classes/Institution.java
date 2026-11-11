package com.example.llt_project_separate.general.standard_classes;

public class Institution {

   private Long id;
   private String school;
   private String classroom;

   public Institution() {
   }

   public Institution(Long id, String school, String classroom) {
      this.id = id;
      this.school = school;
      this.classroom = classroom;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
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
