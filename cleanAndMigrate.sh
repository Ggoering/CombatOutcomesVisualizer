./gradlew flywayClean -Dflyway.url='jdbc:postgresql://localhost:5432/postgres' -Dflyway.user=postgres -Dflyway.password=root
./gradlew flywayMigrate -Dflyway.url='jdbc:postgresql://localhost:5432/postgres' -Dflyway.user=postgres -Dflyway.password=root
