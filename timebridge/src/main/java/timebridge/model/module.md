# Model Refactor & Extension

## EventFactory.java
- createEvent(,)
- createEvents(,)
##

## << Event >>
- id
- interval
- locations
- visability
- attendees

## PersonalEvent implements Event
- summary
- description

## TimeEditEvent implements Event
ourse
Activity
EventFormat

## EventFormat
- Course
- Activity



## CalendarFacade implements Facade
- 
- EventFacade[]

## EventFacade implements Facade
- summary
- description
- interval
- location
- attendees

## CalendarObserver 
(observerar calendarFacade)