FROM gitpod/workspace-full

USER gitpod

RUN bash -c "brew install httpie"
RUN bash -c "brew install oha"

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh && \
    sdk install java 21.0.2-ms && \
    sdk default java 21.0.2-ms"