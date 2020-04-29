database: Postgres
local setup: postgres // root
### Flyway
- Generate a migration:
 - ./gradlew generateMigration -PmName=desired_migration_name
 - Run migrations:  ./gradlew flywayMigrate -Dflyway.url='jdbc:postgresql://localhost:5432/postgres' -Dflyway.user=postgres -Dflyway.password=root
 - Drop everything: ./gradlew flywayClean -Dflyway.url='jdbc:postgresql://localhost:5432/postgres' -Dflyway.user=postgres -Dflyway.password=root
