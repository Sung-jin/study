FROM openjdk:11

ARG TIME_ZONE
ARG PROFILE
ARG BOOTSTRAP_APP
ARG MAX_MEMORY

ENV TIME_ZONE ${TIME_ZONE}
ENV PROFILE ${PROFILE}
ENV BOOTSTRAP_APP ${BOOTSTRAP_APP}
ENV MAX_MEMORY ${MAX_MEMORY}

ADD application.jar /opt/run.jar
RUN echo "Asia/Seoul" > /etc/timezone
RUN dpkg-reconfigure -f noninteractive tzdata

CMD java -server -jar -Duser.timezone=${TIME_ZONE} -XX:-OmitStackTraceInFastThrow -Dspring.profiles.active=${PROFILE} -DAPP_NAME=${BOOTSTRAP_APP} -Xmx${MAX_MEMORY} /opt/run.jar
