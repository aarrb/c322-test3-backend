FROM ubuntu:latest
LABEL authors="archer"

ENTRYPOINT ["top", "-b"]