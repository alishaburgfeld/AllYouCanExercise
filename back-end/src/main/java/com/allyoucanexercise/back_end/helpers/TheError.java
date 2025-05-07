// i have tried:
// 1. disabling csrf
// 2. getting rid of // .requestMatchers("/api/**", "/auth/**").permitAll()
// and .anyRequest().authenticated()) and replacing it with:
// .anyRequest().permitAll()
// below is the error message after I've done that:

// 2025-05-05T09:58:57.590-04:00 DEBUG 2760 --- [back-end] [nio-8080-exec-4]
// o.s.security.web.FilterChainProxy : Securing OPTIONS /api/workouts/full/save
// 2025-05-05T09:58:57.595-04:00 DEBUG 2760 --- [back-end] [nio-8080-exec-5]
// o.s.security.web.FilterChainProxy : Securing POST /api/workouts/full/save
// 2025-05-05T09:58:57.596-04:00 DEBUG 2760 --- [back-end] [nio-8080-exec-5]
// o.s.s.w.a.AnonymousAuthenticationFilter : Set SecurityContextHolder to
// anonymous SecurityContext
// 2025-05-05T09:58:57.597-04:00 DEBUG 2760 --- [back-end] [nio-8080-exec-5]
// o.s.security.web.FilterChainProxy : Secured POST /api/workouts/full/save
// 2025-05-05T09:58:57.609-04:00 ERROR 2760 --- [back-end] [nio-8080-exec-5]
// c.a.back_end.workout.WorkoutController : inside saveFullWorkout, request is
// WorkoutRequestDTO{workoutDetails=WorkoutDetailsDTO{username='null',
// title='null', completedAt=null, workoutNotes='null'},
// workoutExerciseDetails=null}
// 2025-05-05T09:58:57.609-04:00 ERROR 2760 --- [back-end] [nio-8080-exec-5]
// c.a.back_end.workout.WorkoutService : **inside saveFullWorkout in
// workoutService, workoutRequest is
// WorkoutRequestDTO{workoutDetails=WorkoutDetailsDTO{username='null',
// title='null', completedAt=null, workoutNotes='null'},
// workoutExerciseDetails=null}
// 2025-05-05T09:58:57.618-04:00 ERROR 2760 --- [back-end] [nio-8080-exec-5]
// o.a.c.c.C.[.[.[/].[dispatcherServlet] : Servlet.service() for servlet
// [dispatcherServlet] in context with path [] threw exception [Request
// processing failed: java.lang.NullPointerException: Cannot invoke
// "java.util.List.size()" because "workoutExerciseDetails" is null] with root
// cause

// java.lang.NullPointerException: Cannot invoke "java.util.List.size()" because
// "workoutExerciseDetails" is null
// at
// com.allyoucanexercise.back_end.workout.WorkoutService.saveFullWorkout(WorkoutService.java:99)
// ~[classes/:na]
// at
// com.allyoucanexercise.back_end.workout.WorkoutController.saveFullWorkout(WorkoutController.java:72)
// ~[classes/:na]
// at
// java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
// ~[na:na]
// at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]
// at
// org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:255)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:188)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:986)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:891)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1088)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:978)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:914)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
// ~[tomcat-embed-core-10.1.33.jar:6.0]
// at
// org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
// ~[tomcat-embed-core-10.1.33.jar:6.0]
// at
// org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:195)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51)
// ~[tomcat-embed-websocket-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// com.allyoucanexercise.back_end.helpers.HttpLoggingFilter.doFilterInternal(HttpLoggingFilter.java:30)
// ~[classes/:na]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.springframework.web.filter.CompositeFilter$VirtualFilterChain.doFilter(CompositeFilter.java:108)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.security.web.FilterChainProxy.lambda$doFilterInternal$3(FilterChainProxy.java:231)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:365)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.access.intercept.AuthorizationFilter.doFilter(AuthorizationFilter.java:101)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:126)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:120)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:131)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:85)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:100)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:179)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:63)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.session.ConcurrentSessionFilter.doFilter(ConcurrentSessionFilter.java:151)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.session.ConcurrentSessionFilter.doFilter(ConcurrentSessionFilter.java:129)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:107)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:93)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.csrf.CsrfFilter.doFilterInternal(CsrfFilter.java:131)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:91)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.header.HeaderWriterFilter.doHeadersAfter(HeaderWriterFilter.java:90)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:75)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.context.SecurityContextHolderFilter.doFilter(SecurityContextHolderFilter.java:82)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.context.SecurityContextHolderFilter.doFilter(SecurityContextHolderFilter.java:69)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:62)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.session.DisableEncodeUrlFilter.doFilterInternal(DisableEncodeUrlFilter.java:42)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:233)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:191)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.web.filter.CompositeFilter$VirtualFilterChain.doFilter(CompositeFilter.java:113)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.handler.HandlerMappingIntrospector.lambda$createCacheFilter$3(HandlerMappingIntrospector.java:243)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.filter.CompositeFilter$VirtualFilterChain.doFilter(CompositeFilter.java:113)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.filter.CompositeFilter.doFilter(CompositeFilter.java:74)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.security.config.annotation.web.configuration.WebMvcSecurityConfiguration$CompositeFilterChainProxy.doFilter(WebMvcSecurityConfiguration.java:238)
// ~[spring-security-config-6.4.1.jar:6.4.1]
// at
// org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:362)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:278)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:483)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:344)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:397)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:905)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1741)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1190)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:63)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at java.base/java.lang.Thread.run(Thread.java:1583) ~[na:na]

// 2025-05-05T09:58:57.625-04:00 DEBUG 2760 --- [back-end] [nio-8080-exec-5]
// o.s.security.web.FilterChainProxy : Securing POST /error
// 2025-05-05T09:58:57.625-04:00 DEBUG 2760 --- [back-end] [nio-8080-exec-5]
// o.s.security.web.FilterChainProxy : Secured POST /error
// 2025-05-05T09:58:57.633-04:00 DEBUG 2760 --- [back-end] [nio-8080-exec-5]
// o.s.s.w.a.AnonymousAuthenticationFilter : Set SecurityContextHolder to
// anonymous SecurityContext

// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************

// 3. I tried adding the annotation @EnableWebSecurity to force it to not use
// the default security chain, very similar error message:
// 2025-05-05T10:04:14.973-04:00 DEBUG 4324 --- [back-end] [nio-8080-exec-1]
// o.s.security.web.FilterChainProxy : Securing OPTIONS /api/workouts/full/save
// 2025-05-05T10:04:14.979-04:00 DEBUG 4324 --- [back-end] [nio-8080-exec-2]
// o.s.security.web.FilterChainProxy : Securing POST /api/workouts/full/save
// 2025-05-05T10:04:14.981-04:00 DEBUG 4324 --- [back-end] [nio-8080-exec-2]
// o.s.s.w.a.AnonymousAuthenticationFilter : Set SecurityContextHolder to
// anonymous SecurityContext
// 2025-05-05T10:04:14.982-04:00 DEBUG 4324 --- [back-end] [nio-8080-exec-2]
// o.s.security.web.FilterChainProxy : Secured POST /api/workouts/full/save
// 2025-05-05T10:04:14.990-04:00 ERROR 4324 --- [back-end] [nio-8080-exec-2]
// c.a.back_end.workout.WorkoutController : inside saveFullWorkout, request is
// WorkoutRequestDTO{workoutDetails=WorkoutDetailsDTO{username='null',
// title='null', completedAt=null, workoutNotes='null'},
// workoutExerciseDetails=null}
// 2025-05-05T10:04:14.990-04:00 ERROR 4324 --- [back-end] [nio-8080-exec-2]
// c.a.back_end.workout.WorkoutService : **inside saveFullWorkout in
// workoutService, workoutRequest is
// WorkoutRequestDTO{workoutDetails=WorkoutDetailsDTO{username='null',
// title='null', completedAt=null, workoutNotes='null'},
// workoutExerciseDetails=null}
// 2025-05-05T10:04:14.998-04:00 ERROR 4324 --- [back-end] [nio-8080-exec-2]
// o.a.c.c.C.[.[.[/].[dispatcherServlet] : Servlet.service() for servlet
// [dispatcherServlet] in context with path [] threw exception [Request
// processing failed: java.lang.NullPointerException: Cannot invoke
// "java.util.List.size()" because "workoutExerciseDetails" is null] with root
// cause

// java.lang.NullPointerException: Cannot invoke "java.util.List.size()" because
// "workoutExerciseDetails" is null
// at
// com.allyoucanexercise.back_end.workout.WorkoutService.saveFullWorkout(WorkoutService.java:99)
// ~[classes/:na]
// at
// com.allyoucanexercise.back_end.workout.WorkoutController.saveFullWorkout(WorkoutController.java:72)
// ~[classes/:na]
// at
// java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
// ~[na:na]
// at java.base/java.lang.reflect.Method.invoke(Method.java:580) ~[na:na]
// at
// org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:255)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:188)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:986)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:891)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1088)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:978)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:914)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
// ~[tomcat-embed-core-10.1.33.jar:6.0]
// at
// org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
// ~[tomcat-embed-core-10.1.33.jar:6.0]
// at
// org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:195)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51)
// ~[tomcat-embed-websocket-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// com.allyoucanexercise.back_end.helpers.HttpLoggingFilter.doFilterInternal(HttpLoggingFilter.java:30)
// ~[classes/:na]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.springframework.web.filter.CompositeFilter$VirtualFilterChain.doFilter(CompositeFilter.java:108)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.security.web.FilterChainProxy.lambda$doFilterInternal$3(FilterChainProxy.java:231)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:365)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.access.intercept.AuthorizationFilter.doFilter(AuthorizationFilter.java:101)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:126)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:120)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:131)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:85)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:100)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:179)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:63)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.session.ConcurrentSessionFilter.doFilter(ConcurrentSessionFilter.java:151)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.session.ConcurrentSessionFilter.doFilter(ConcurrentSessionFilter.java:129)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:107)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:93)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.csrf.CsrfFilter.doFilterInternal(CsrfFilter.java:131)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:91)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.header.HeaderWriterFilter.doHeadersAfter(HeaderWriterFilter.java:90)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:75)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.context.SecurityContextHolderFilter.doFilter(SecurityContextHolderFilter.java:82)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.context.SecurityContextHolderFilter.doFilter(SecurityContextHolderFilter.java:69)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:62)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.session.DisableEncodeUrlFilter.doFilterInternal(DisableEncodeUrlFilter.java:42)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:233)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:191)
// ~[spring-security-web-6.4.1.jar:6.4.1]
// at
// org.springframework.web.filter.CompositeFilter$VirtualFilterChain.doFilter(CompositeFilter.java:113)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.servlet.handler.HandlerMappingIntrospector.lambda$createCacheFilter$3(HandlerMappingIntrospector.java:243)
// ~[spring-webmvc-6.2.0.jar:6.2.0]
// at
// org.springframework.web.filter.CompositeFilter$VirtualFilterChain.doFilter(CompositeFilter.java:113)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.filter.CompositeFilter.doFilter(CompositeFilter.java:74)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.security.config.annotation.web.configuration.WebMvcSecurityConfiguration$CompositeFilterChainProxy.doFilter(WebMvcSecurityConfiguration.java:238)
// ~[spring-security-config-6.4.1.jar:6.4.1]
// at
// org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:362)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:278)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
// ~[spring-web-6.2.0.jar:6.2.0]
// at
// org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:483)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:344)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:397)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:905)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1741)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1190)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at
// org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:63)
// ~[tomcat-embed-core-10.1.33.jar:10.1.33]
// at java.base/java.lang.Thread.run(Thread.java:1583) ~[na:na]

// 2025-05-05T10:04:15.008-04:00 DEBUG 4324 --- [back-end] [nio-8080-exec-2]
// o.s.security.web.FilterChainProxy : Securing POST /error
// 2025-05-05T10:04:15.009-04:00 DEBUG 4324 --- [back-end] [nio-8080-exec-2]
// o.s.s.w.a.AnonymousAuthenticationFilter : Set SecurityContextHolder to
// anonymous SecurityContext
// 2025-05-05T10:04:15.009-04:00 DEBUG 4324 --- [back-end] [nio-8080-exec-2]
// o.s.s.w.a.Http403ForbiddenEntryPoint : Pre-authenticated entry point called.
// Rejecting access

// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************

// 4. I tried disabling cors, but that was even worse and I got these errors
// 025-05-05T10:12:08.095-04:00 DEBUG 5765 --- [back-end] [nio-8080-exec-1]
// o.s.s.w.a.AnonymousAuthenticationFilter : Set SecurityContextHolder to
// anonymous SecurityContext
// 2025-05-05T10:12:08.095-04:00 DEBUG 5765 --- [back-end] [nio-8080-exec-2]
// o.s.s.w.a.AnonymousAuthenticationFilter : Set SecurityContextHolder to
// anonymous SecurityContext
// 2025-05-05T10:12:08.096-04:00 DEBUG 5765 --- [back-end] [nio-8080-exec-1]
// o.s.security.web.FilterChainProxy : Secured OPTIONS /auth/checkusersession
// 2025-05-05T10:12:08.096-04:00 DEBUG 5765 --- [back-end] [nio-8080-exec-2]
// o.s.security.web.FilterChainProxy : Secured OPTIONS /auth/checkusersession
// 2025-05-05T10:12:08.106-04:00 INFO 5765 --- [back-end] [nio-8080-exec-2]
// c.a.back_end.helpers.HttpLoggingFilter : Request
// 2025-05-05T10:12:08.106-04:00 INFO 5765 --- [back-end] [nio-8080-exec-1]
// c.a.back_end.helpers.HttpLoggingFilter : Request
// 2025-05-05T10:12:08.107-04:00 INFO 5765 --- [back-end] [nio-8080-exec-2]
// c.a.back_end.helpers.HttpLoggingFilter : Response Invalid CORS request
// 2025-05-05T10:12:08.107-04:00 INFO 5765 --- [back-end] [nio-8080-exec-1]
// c.a.back_end.helpers.HttpLoggingFilter : Response Invalid CORS request
// 2025-05-05T10:12:12.601-04:00 DEBUG 5765 --- [back-end] [nio-8080-exec-3]
// o.s.security.web.FilterChainProxy : Securing OPTIONS /auth/login
// 2025-05-05T10:12:12.602-04:00 DEBUG 5765 --- [back-end] [nio-8080-exec-3]
// o.s.s.w.a.AnonymousAuthenticationFilter : Set SecurityContextHolder to
// anonymous SecurityContext
// 2025-05-05T10:12:12.603-04:00 DEBUG 5765 --- [back-end] [nio-8080-exec-3]
// o.s.security.web.FilterChainProxy : Secured OPTIONS /auth/login
// 2025-05-05T10:12:12.604-04:00 INFO 5765 --- [back-end] [nio-8080-exec-3]
// c.a.back_end.helpers.HttpLoggingFilter : Request
// 2025-05-05T10:12:12.604-04:00 INFO 5765 --- [back-end] [nio-8080-exec-3]
// c.a.back_end.helpers.HttpLoggingFilter : Response Invalid CORS request

// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************

// 5. I tried adding:
// @Autowired
// private AuthenticationConfiguration authenticationConfiguration;
// and
// @Bean
// public AuthenticationManager authenticationManager() throws Exception {
// return authenticationConfiguration.getAuthenticationManager();
// }

// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************
// *****************************************************************

// 6. Tried just getting rid of the line: // .anyRequest().authenticated())
// same basic error:
// 2025-05-05T10:29:23.370-04:00 DEBUG 8527 --- [back-end] [nio-8080-exec-2]
// o.s.security.web.FilterChainProxy : Securing POST /error
// 2025-05-05T10:29:23.371-04:00 DEBUG 8527 --- [back-end] [nio-8080-exec-2]
// o.s.s.w.a.AnonymousAuthenticationFilter : Set SecurityContextHolder to
// anonymous SecurityContext
// 2025-05-05T10:29:23.371-04:00 DEBUG 8527 --- [back-end] [nio-8080-exec-2]
// o.s.s.w.a.Http403ForbiddenEntryPoint : Pre-authenticated entry point called.
// Rejecting access

// 7. I tried changing the endpoint to /auth/workouts... since my login/singup
// are working, but still the same error.

// 8. I tried changing it to .httpBasic(withDefaults());, but when I sent my
// post call a popup came asking for my username/password.

// 9. I tried manually setting the authentication when a user log-ins:
// i added this in my userService:
// public void setSecurityContext(User user) {
// SecurityContext context = SecurityContextHolder.createEmptyContext();
// Authentication authentication = new
// TestingAuthenticationToken(user.getUsername(), user.getPassword());
// context.setAuthentication(authentication);

// SecurityContextHolder.setContext(context);
// }
// https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html#servlet-authentication-authentication

// 10. I added this to see if it would show an authenticated user:
// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
// System.out.println("Authenticated user: " + auth.getName());
// it DOES print out the same thing in both methods.

// 11. I also tried changing it to: .requestMatchers("/api/**",
// "/auth/**").anonymous() since its being sent to anonymous user, but I'm still
// getting the same errors

// 12. I tried adding this after googling "why is spring 6 Setting
// SecurityContextHolder to anonymous SecurityContext"
// .securityContext((securityContext) -> securityContext
// .securityContextRepository(new HttpSessionSecurityContextRepository()))
// more notes on that here:
// https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html

// also tried:
// .securityContext((securityContext) -> securityContext
// .securityContextRepository(
// new RequestAttributeSecurityContextRepository()))