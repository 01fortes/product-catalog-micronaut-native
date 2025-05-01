# syntax=docker/dockerfile:1.6
############################################################
# 1️⃣  СЛОЙ СБОРКИ
############################################################
FROM ghcr.io/graalvm/native-image-community:23.0.2-ol8 AS build

# ──> добавляем xargs (пакет findutils) ─────────────────────
RUN microdnf install -y findutils && microdnf clean all
# если понадобится tar/gzip для graal-agent, добавь:product-catalog-micronaut-native
# RUN microdnf install -y findutils tar gzip && microdnf clean all
# ───────────────────────────────────────────────────────────

WORKDIR /app
COPY . .

RUN --mount=type=cache,target=/home/gradle/.gradle \
    ./gradlew nativeCompile \
      -Pmicronaut.native-image.args="--enable-preview" \
      --no-daemon

############################################################
# 2️⃣  МИНИМАЛЬНЫЙ РАНТАЙМ
############################################################
FROM container-registry.oracle.com/os/oraclelinux:8-slim
WORKDIR /app
COPY --from=build /app/build/native/nativeCompile/product-catalog-micronaut-native .
EXPOSE 8080
ENTRYPOINT ["./product-catalog-micronaut-native", "-Xmx256m", "-XX:MaxDirectMemorySize=256m"]