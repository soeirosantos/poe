POE - Personal Organizer Environment
------------------------------------

[![Build Status](https://travis-ci.org/soeirosantos/poe.svg?branch=master)](https://travis-ci.org/soeirosantos/poe) [![Code Coverage](https://img.shields.io/codecov/c/github/soeirosantos/poe/master.svg)](https://codecov.io/github/soeirosantos/poe?branch=master)

A simple _(in-progress)_ application to keep personal data like notes, memos, bookmarks, feed reader, etc.

## Running locally

`$ git clone https://github.com/soeirosantos/soeirosantos/poe.git`

`$ ./gradlew clean build && java -jar build/libs/poe-1.0.0-SNAPSHOT.jar`

## In the IDE

* Import as a gradle application
* Run `PoeApplication.java#main()` (in debug mode if necessary)

## Usage

For a basic use of the API see `src/it/java/br/com/soeirosantos/poe/NotesITest`

Login Example Request

```
curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{"username": "admin@poe.com.br","password": "test1234"}' "http://localhost:8080/api/auth/login"
```

Example Request (replace `replace-me-with-a-json-web-token` by the token got from the previous request)

```
curl -X GET -H "Authorization: Bearer replace-me-with-a-json-web-token" -H "Cache-Control: no-cache" "http://localhost:8080/api/me"
```

TODO: Configure db migrations using Flyway
