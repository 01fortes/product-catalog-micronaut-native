# 1. Билдим внутри правильного образа
FROM ghcr.io/graalvm/native-image:ol8-java21-2024-04-16 AS build

WORKDIR /app

# Копируем всё
COPY . .

# Генерируем native-образ
RUN ./gradlew nativeCompile --no-daemon

# 2. Минимальный финальный образ
FROM busybox:glibc

WORKDIR /app

COPY --from=build /app/build/native/nativeCompile/prodict-catalog-micronaut-native .

EXPOSE 8080

CMD ["./prodict-catalog-micronaut-native"]