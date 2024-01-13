import {HttpClient, HttpParams, HttpResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {getHttpHeaders} from '@app/common/constants/constants';
import {resourceServerUrl} from '@app/common/constants/server-settings';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class DashboardService {

    constructor(private http: HttpClient) {
    }

    getActiveUserList(umsRole: string, currentOffice: string, offset: number = 0, limit: number = 10): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/dashboard/active-list`;
        return this.http.get(url, {
            params: new HttpParams()
                .set('umsRole', umsRole.toString())
                .set('currentOffice', currentOffice.toString())
                .set('offset', offset.toString())
                .set('limit', limit.toString()),
            headers: getHttpHeaders(), observe: 'response'
        });
    }
    getPieChartCount(umsRole: string, currentOffice: string, offset: number = 0, limit: number = 10): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/dashboard/pie-count-list`;
        return this.http.get(url, {
            params: new HttpParams()
                .set('umsRole', umsRole.toString())
                .set('currentOffice', currentOffice.toString())
                .set('offset', offset.toString())
                .set('limit', limit.toString()),
            headers: getHttpHeaders(), observe: 'response'
        });
    }
    getBarChartCount(umsRole: string, currentOffice: string, requestType: string ): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/dashboard/bar-chart-list`;
        return this.http.get(url, {
            params: new HttpParams()
                .set('umsRole', umsRole.toString())
                .set('currentOffice', currentOffice.toString())
                .set('requestType', requestType.toString()),
            headers: getHttpHeaders(), observe: 'response'
        });
    }
    getLineChartCount(umsRole: string, currentOffice: string, requestType: string ): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/dashboard/line-chart-list`;
        return this.http.get(url, {
            params: new HttpParams()
                .set('umsRole', umsRole.toString())
                .set('currentOffice', currentOffice.toString())
                .set('requestType', requestType.toString()),
            headers: getHttpHeaders(), observe: 'response'
        });
    }

    getDeactiveUserList(umsRole: string, currentOffice: string, offset: number = 0, limit: number = 10): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/dashboard/deactive-list`;
        return this.http.get(url, {
            params: new HttpParams()
                .set('umsRole', umsRole.toString())
                .set('currentOffice', currentOffice.toString())
                .set('offset', offset.toString())
                .set('limit', limit.toString()),
            headers: getHttpHeaders(), observe: 'response'
        });
    }

    getPendingUserList(umsRole: string, currentOffice: string, offset: number = 0, limit: number = 10): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/dashboard/pending-list`;
        return this.http.get(url, {
            params: new HttpParams()
                .set('umsRole', umsRole.toString())
                .set('currentOffice', currentOffice.toString())
                .set('offset', offset.toString())
                .set('limit', limit.toString()),
            headers: getHttpHeaders(), observe: 'response'
        });
    }

    getUserProfileInfo(userId: string): Observable<HttpResponse<any>> {
        return this.http.get(`${resourceServerUrl}/v1/operator/getOperatorByUserId/${userId}`, {
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

}
