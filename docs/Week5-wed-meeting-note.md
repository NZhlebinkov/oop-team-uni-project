# Week 5 Wednesday meeting notes
Date: 11/03/2020\
Note taker: Justin Jo

## Schema
- Details
    - _**ID**_
    - name *
    - description *
    - image *
- User
    - _email_
    - _**username**_
    - Password
    - @Details
    - ~~firstname~~
    - ~~lastname~~
    - UserKind
- Building
    - _**ID**_
    - @Details
    - @FoodCourt *
    - @Reservables +
    - @OpeningTime
    - @Map<Reservable, Timeslots> +
- FoodCourt
    - _**ID**_
    - @Details
    - @FoodList
- FoodCourt - Food
    - @Details
- Reservable
    - _**ID**_
    - @Details
- Reservable - Room
    - Capacity
    - hasProjector
    - forEmployee
- Reservable - Bike
- Reservation
    - @User
    - @Reservable
    - @Timeslot
    - _**ID**_
- Timeslot
    - _**ID**_
    - index
    - isAvailable
- OpeningTimes
    - _**ID**_
    - openingHour
    - closingHour
    - @Date
- Date
    - Year
    - Month
    - Day
    - Week
    - isHoliday
- FoodOrder
    - FoodItems
    - _**ID**_
    - @User


## Labels
- @: foreign key
- *: nullable
- bold: primary
- italics: unique
- +: for code implementation only