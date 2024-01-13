import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {resourceServerUrl} from '@app/common/constants/server-settings';
import {getHttpHeaders} from '@app/common/constants/constants';

@Injectable({
    providedIn: 'root'
})
export class SysadminService {

    constructor(private http: HttpClient) {
    }

    getCustomsHouseList(): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/api/v1/office`;
        return this.http.get(url, {
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    saveCustomsHouse(requestData: any): Observable<any> {
        const url: string = `${resourceServerUrl}/api/v1/office`;
        return this.http.post(url, requestData, {
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    updateCustomsHouse(requestData: any, oid: any): Observable<any> {
        const url: string = `${resourceServerUrl}/api/v1/office/${oid}`;
        return this.http.put(url, requestData, {
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    getCustomsOfficeByOid(oid: any): Observable<any> {
        const url: string = `${resourceServerUrl}/api/v1/office/${oid}`;
        return this.http.get(url,
            {
                headers: getHttpHeaders(), observe: 'response'
            });
    }

    getOperatorList(): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/operator`;
        return this.http.get(url, {
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    saveOperator(requestData: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/operator`;
        return this.http.post(url, requestData, {
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    updateOperator(requestData: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/operator/`;
        return this.http.put(url, requestData, {
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    getOperatorByOid(oid: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/operator/${oid}`;
        return this.http.get(url,
            {
                headers: getHttpHeaders(),
                observe: 'response'
            });
    }

    getUserIdList(userId: string): Observable<HttpResponse<any>> {        
        const url: string = `${resourceServerUrl}/v1/operator/verify/${userId}`;
        return this.http.get(url, {
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    getOperatorPassword(userId: string): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/operator/password/${userId}`;
        return this.http.get(url, {
            headers: getHttpHeaders(),
            observe: 'response',
            responseType: 'test' as 'json'
        });
    }
}
