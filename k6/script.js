import http from 'k6/http';
import {check, sleep} from 'k6';

export let options = {
    stages: [
        {duration: '240s', target: 1000},
    ],
    thresholds: {
        http_req_duration: ['p(90) < 200', 'p(95) < 500', 'p(99.9) < 1200'],
    }
};
export default function () {
    let headers = {'Content-Type': 'application/json'};
    let res = http.post('http://localhost:8090/event/', {}, {headers: headers});
    check(res, {'status was 201': (r) => r.status === 201});
    sleep(1);
}
