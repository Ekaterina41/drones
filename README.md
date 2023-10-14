## Drones

[[_TOC_]]

---

:scroll: **START**

### Instructions

#### Build project
Before building the project, make sure that you have jdk 17+ and maven installed in the path.
To build the application, execute this command in the project root:

```
mvn install
```

#### Run the application
To run the jar, use this command:

```
mvn spring-boot:run
```

#### Run tests
To run the tests, use this command:

```
mvn test
```

#### Requests

1. Register drone

```
POST {root_url}/drone/register
Body:
{
   "serialNumber": "DRN011",
   "model": "LIGHTWEIGHT",
   "weightLimit": 100,
   "batteryLevel": 50
}
```

2. Load drone with 2 medication items with images:
```
POST {root_url}/drone/2/load?isFinalLoad=true
--form 'medications[0].name="54ygter"' \
--form 'medications[0].weight="10"' \
--form 'medications[0].code="4SJK"' \
--form 'medications[0].image=@"/C:/url/example/medicine_image_1.jpg"' \
--form 'medications[1].name="7twdhgu"' \
--form 'medications[1].weight="20"' \
--form 'medications[1].code="8GHS"' \
--form 'medications[1].image=@"/C:/url/example/medicine_image_2.jpg"'
```

3. Get loaded medications

```
GET {root_url}/drone/2/items
```

4. Get available drones

```
GET {root_url}/drone/available
```

5. Get battery level

```
GET {root_url}/drone/2/batteryLevel
```

---

:scroll: **END** 
