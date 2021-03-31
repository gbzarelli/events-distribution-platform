db = db.getSiblingDB('wh_db');

db.createCollection('wh_filters');

db.wh_filters.insertMany([
 {
    org: 'helpdev',
    filters: [
      {    
        filter: 'EVENT_A',
        whCallback: 'http://localhost:8080/wh'
      },
      {    
        filter: 'EVENT_B',
        whCallback: 'http://localhost:8081/wh'
      }
    ]
  },
  {
    org: 'github',
    filters: [
      {    
        filter: 'EVENT_C',
        whCallback: 'http://localhost:8082/wh'
      }
    ]
  }  
]);