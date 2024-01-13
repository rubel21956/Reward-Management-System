import {HttpBackend, HttpClient, HttpParams, HttpResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {getHttpHeaders} from '@app/common/constants/constants';
import {resourceServerUrl} from '@app/common/constants/server-settings';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class OfficeService {

    constructor(private http: HttpClient, private httpbackend: HttpBackend) {
        //Token Bypass
        // this.http = new HttpClient(httpbackend);
    }

    saveInfo(requestData: any): Observable<any> {
        const url: string = `${resourceServerUrl}/public/v1/office/save`;

        return this.http.post(url, requestData,
            {headers: getHttpHeaders(), observe: 'response'});
    }

    getAwRoleList(): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/public/v1/awrole`;

        return this.http.get(url, {
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    getByOid(oid: any): Observable<any> {

        const url: string = `${resourceServerUrl}/public/v1/office/${oid}`;

        return this.http.get(url,
            {headers: getHttpHeaders(), observe: 'response'});
    }

    updateInfo(requestData: any, oid): Observable<any> {
        const url: string = `${resourceServerUrl}/public/v1/office/${oid}`;
        return this.http.put(url, requestData,
            {headers: getHttpHeaders(), observe: 'response'});
    }

    getOfficeList(): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/public/v1/office/get-list`;
        return this.http.get(url,
            {headers: getHttpHeaders(), observe: 'response'});
    }

    getOfficeListforSystemAdmin(offset: number = 0, limit: number = 10, searchText: string): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/public/v1/office/get-list`;
        return this.http.get(url, {
            params: new HttpParams()
                .set('searchText', searchText)
                .set('offset', offset.toString())
                .set('limit', limit.toString()),
            headers: getHttpHeaders(), observe: 'response'
        });
    }
}
