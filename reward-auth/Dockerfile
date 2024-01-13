#################
## BUILD Stage ##
#################
FROM gradle:6.8.1-jdk8 as BUILDER

WORKDIR /app

# Only copy dependency-related files
COPY build.gradle settings.gradle /app/

# Only download dependencies
# Since no source code has been copied yet, build will fail after
# downloading the dependencies. Redirect the expected build failure
# output to /dev/null.
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true

COPY ./ /app/

# Do the actual build
RUN gradle clean build --no-daemon


###############
## RUN Stage ##
###############
FROM openjdk:8-jre-alpine

# ARGs for this stage
ARG USER_NAME=app_runner
ARG USER_ID=1000
ARG GROUP_NAME=${USER_NAME}
ARG GROUP_ID=1000

ARG LOGBACK_PATH=/var/log/app

WORKDIR /app

# Create the user. This command is specific to Alpine Linux.
RUN addgroup -g $GROUP_ID -S $GROUP_NAME && adduser -u $USER_ID -S $USER_NAME -G $GROUP_NAME

# $USER_NAME needs access to the /app directory to run the app and
# write log files in the /app/logs directory.
COPY --from=BUILDER --chown=$USER_NAME:$GROUP_NAME /app/build/libs/*.jar /app/app.jar

# $USER_NAME needs read & write access to the $LOGBACK_PATH directory.
RUN mkdir -p $LOGBACK_PATH && chown -R $USER_NAME:$GROUP_NAME $LOGBACK_PATH

# Set the default user to $USER_NAME
USER $USER_NAME

EXPOSE 8090

CMD ["java", "-jar", "/app/app.jar"]