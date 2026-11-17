package com.project.ohl.app;

import com.project.ohl.data.remote.RemoteAuthenticationRepository;
import com.project.ohl.data.remote.RemoteLearningRepository;
import com.project.ohl.data.remote.RetrofitService;
import com.project.ohl.domain.repository.AuthenticationRepository;
import com.project.ohl.domain.repository.LearningRepository;

public final class ApplicationGraph {
    private ApplicationGraph() {}

    private static final class Holder {
        private static final LearningRepository LEARNING = new RemoteLearningRepository(RetrofitService.getRetrofit());
        private static final AuthenticationRepository AUTHENTICATION =
                new RemoteAuthenticationRepository(RetrofitService.getRetrofit(), RetrofitService.tokens());
    }

    public static LearningRepository learning() {
        return Holder.LEARNING;
    }

    public static AuthenticationRepository authentication() {
        return Holder.AUTHENTICATION;
    }
}
