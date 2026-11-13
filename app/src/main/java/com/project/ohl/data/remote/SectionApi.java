package com.project.ohl.data.remote;

import com.project.ohl.domain.model.Section;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SectionApi {
    @GET("/api/sections")
    Call<List<Section>> getAllSections();
}
