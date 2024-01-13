import { Injectable } from '@angular/core';
import { HttpClient, HttpBackend } from '@angular/common/http';
import { Observable } from 'rxjs';

import { resourceServerUrl } from '../../common/constants/server-settings';
import { getHttpHeaders } from '../../common/constants/constants';

@Injectable({
  providedIn: 'root'
})
export class ForgotUseridPasswordService {

    private passwordRecoveryURI: string = `${resourceServerUrl}/public/v1/forget-password`;

    constructor(private http: HttpClient, private httpBackend: HttpBackend) {
        this.http = new HttpClient(httpBackend);
    }

    passwordRecovery(userId: any, nid: any, recoveryType: any): Observable<any> {
        return this.http.post(this.passwordRecoveryURI,
            { "userId": userId,
              "nid": "",
              "recoveryType": ""
            },
            { headers: getHttpHeaders(), observe: 'response' });
    }
    // saveInfo(userId: any): Observable<any> {
    //     const url: string = `${resourceServerUrl}/public/v1/office/save`;

    //     return this.http.post(url, requestData,
    //         {headers: getHttpHeaders(), observe: 'response'});
    // }
}
