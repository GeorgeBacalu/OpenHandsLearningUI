package com.project.ohl.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.project.ohl.domain.model.City;
import com.project.ohl.domain.model.County;
import com.project.ohl.domain.model.School;
import com.project.ohl.domain.repository.LearningRepository;
import com.project.ohl.presentation.state.AsyncRequest;
import com.project.ohl.presentation.state.DebouncedAction;
import com.project.ohl.presentation.state.DelayScheduler;
import com.project.ohl.presentation.state.UiState;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;

public final class SchoolDirectoryViewModel extends ViewModel {
    private final LearningRepository repository;
    private final SavedStateHandle savedState;
    private final AsyncRequest<List<County>> counties;
    private final AsyncRequest<List<City>> cities;
    private final AsyncRequest<List<School>> schools;
    private final DebouncedAction citySearch;
    private final DebouncedAction schoolSearch;

    public SchoolDirectoryViewModel(LearningRepository repository, SavedStateHandle savedState, Executor mainThread, DelayScheduler scheduler) {
        this.repository = repository;
        this.savedState = savedState;
        counties = new AsyncRequest<>(mainThread);
        cities = new AsyncRequest<>(mainThread);
        schools = new AsyncRequest<>(mainThread);
        citySearch = new DebouncedAction(scheduler, mainThread);
        schoolSearch = new DebouncedAction(scheduler, mainThread);
    }

    public LiveData<UiState<List<County>>> counties() { return counties.state(); }
    public LiveData<UiState<List<City>>> cities() { return cities.state(); }
    public LiveData<UiState<List<School>>> schools() { return schools.state(); }
    public UUID countyId() { return id("county"); }
    public UUID cityId() { return id("city"); }
    public UUID schoolId() { return id("school"); }
    private UUID id(String key) { String value = savedState.get(key); return value == null ? null : UUID.fromString(value); }
    private void select(String key, UUID value) { savedState.set(key, value == null ? null : value.toString()); }

    public void load() {
        if (counties.current().status() == UiState.Status.IDLE) counties.start(repository.getCounties());
        if (countyId() != null && cities.current().status() == UiState.Status.IDLE) searchCities(query("cities.query"));
        if (cityId() != null && schools.current().status() == UiState.Status.IDLE) searchSchools(query("schools.query"));
    }

    public void selectCounty(UUID id) {
        citySearch.cancel();
        savedState.set("cities.query", "");
        select("county", id);
        selectCity(null);
        cities.replace(List.of());
    }

    public void selectCity(UUID id) {
        schoolSearch.cancel();
        savedState.set("schools.query", "");
        select("city", id);
        selectSchool(null);
        schools.replace(List.of());
    }

    public void editCityQuery(String query) {
        selectCity(null);
        cities.cancel();
        savedState.set("cities.query", query);
        citySearch.submit(() -> searchCities(query));
    }

    public void editSchoolQuery(String query) {
        selectSchool(null);
        schools.cancel();
        savedState.set("schools.query", query);
        schoolSearch.submit(() -> searchSchools(query));
    }

    private String query(String key) { String value = savedState.get(key); return value == null ? "" : value; }

    public void selectSchool(UUID id) { if (id != null) schoolSearch.cancel(); select("school", id); }

    public void searchCities(String query) {
        citySearch.cancel();
        savedState.set("cities.query", query);
        if (countyId() != null) cities.start(repository.getCities(countyId(), query, 0, 50));
    }
    public void searchSchools(String query) {
        schoolSearch.cancel();
        savedState.set("schools.query", query);
        if (cityId() != null) schools.start(repository.getSchools(cityId(), query, 0, 50));
    }
    @Override protected void onCleared() {
        citySearch.close(); schoolSearch.close();
        counties.close(); cities.close(); schools.close();
    }
}
