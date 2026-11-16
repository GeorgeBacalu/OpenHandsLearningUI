package com.project.ohl;

import com.project.ohl.domain.repository.LearningRepository;
import com.project.ohl.domain.repository.Request;
import com.project.ohl.domain.repository.Result;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

final class ControlledLearningRepository {
    static final class Call {
        final String method;
        final Object[] arguments;
        final Consumer<Result<Object>> callback;
        boolean cancelled;
        Call(String method, Object[] arguments, Consumer<Result<Object>> callback) {
            this.method = method; this.arguments = arguments; this.callback = callback;
        }
        void succeed(Object value) { callback.accept(Result.success(value)); }
    }
    final List<Call> calls = new ArrayList<>();
    final LearningRepository repository = LearningRepository.class.cast(Proxy.newProxyInstance(
            LearningRepository.class.getClassLoader(), new Class<?>[]{LearningRepository.class},
            (proxy, method, args) -> (Request<Object>) callback -> {
                var call = new Call(method.getName(), args == null ? new Object[0] : args.clone(), callback);
                calls.add(call);
                return () -> call.cancelled = true;
            }));
}
