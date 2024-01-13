import {Injectable} from '@angular/core';
import {HttpClient, HttpParams, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {resourceServerUrl} from '../../../common/constants/server-settings';
import {getHttpHeaders} from '../../../common/constants/constants';

@Injectable({
    providedIn: 'root'
})
export class NbrApplicationService {

    constructor(private http: HttpClient) {
    }

    getStepFiveFileDownload(refId: string): Observable<any> {
        const url = `${resourceServerUrl}/v1/application/five/file/${refId}`;
        return this.http.get(url, {
            headers: getHttpHeaders(),
            observe: 'response',
            responseType: 'blob',
        });
    }

    getStepTwoFileDownload(refId: string): Observable<any> {
        const url = `${resourceServerUrl}/v1/application/two/file/${refId}`;
        return this.http.get(url, {
            headers: getHttpHeaders(),
            observe: 'response',
            responseType: 'blob',
        });
    }

    // This Api Method is for Download PURBO BOTI BILL OF ENTRY 
    // Modified By Arif 

    getStepOneFileDownload(refId: string): Observable<any> {
        const url = `${resourceServerUrl}/v1/application/one/file/${refId}`;
        return this.http.get(url, {
            headers: getHttpHeaders(),
            observe: 'response',
            responseType: 'blob',
        });
    }


    getApplicationList(): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/application/ForNbrAdmin`;
        return this.http.get(url, {
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    getStatusWiseApplicationList(applicationStatus: any): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/application/search/${applicationStatus}`;
        return this.http.get(url, {
            params: new HttpParams()
                .set('applicationStatus', applicationStatus.toString()),
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    getStatusAndOfficeWiseApplicationList(applicationStatus: string, officeName: string, applicationDate: string): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/application/searchBystatusAndOfficeName`;
        return this.http.get(url, {
            params: new HttpParams()
                .set('applicationStatus', applicationStatus)
                .set('officeName', officeName)
                .set('applicationDate', applicationDate),
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    getApplicationOfSattlement(applicationStatus: string, officeName: string, applicationDate: string): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/application/getApplicationOfSattlement`;
        return this.http.get(url, {
            params: new HttpParams()
                .set('applicationStatus', applicationStatus)
                .set('officeName', officeName)
                .set('applicationDate', applicationDate),
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }


    getApplicationDetails(id: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/application/${id}`;
        return this.http.get(url,
            {
                headers: getHttpHeaders(), observe: 'response'
            });
    }

    updateFinalApprovalByNbr(id: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/application/nbr/step-three-submit/${id}`;
        return this.http.put(url,
            {headers: getHttpHeaders(), observe: 'response'});
    }

    updateFinalApprovalByNbrForSattlement(id: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/application/nbr/step-three-submit-for-sattlement/${id}`;
        return this.http.put(url,
            {headers: getHttpHeaders(), observe: 'response'});
    }
    

    updateApplicationCustomsStepOneUsingPUT(requestData: any, id: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/application/nbr/step-one/${id}`;
        return this.http.put(url, requestData,
            {headers: getHttpHeaders(), observe: 'response'});
    }

    updateApplicationCustomsStepTwoUsingPUT(requestData: any, id: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/application/nbr/step-two/${id}`;
        return this.http.put(url, requestData,
            {headers: getHttpHeaders(), observe: 'response'});
    }

    updateApplicationCustomsStepThreeUsingPUT(requestData: any, id: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/application/nbr/step-three/${id}`;
        return this.http.put(url, requestData,
            {headers: getHttpHeaders(), observe: 'response'});
    }


    applicationStepOneView(oid: string): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/report/application-step-one`;
        
        return this.http.get(url,
                  {
                    params: new HttpParams()
                    .set('oid', oid.toString()),
                    headers: getHttpHeaders(), 
                    observe: 'response',
                    responseType: 'blob'            
                  });
      }

      applicationStepTwoView(oid: string): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/report/application-step-two`;

        return this.http.get(url,
                  {
                    params: new HttpParams()
                    .set('oid', oid.toString()),
                    headers: getHttpHeaders(), 
                    observe: 'response',
                    responseType: 'blob'            
                  });
      
      }

      applicationStepThreeView(oid: string): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/report/application-step-three`;

        return this.http.get(url,
                  {
                    params: new HttpParams()
                    .set('oid', oid.toString()),
                    headers: getHttpHeaders(), 
                    observe: 'response',
                    responseType: 'blob'            
                  });
      
      }

      applicationStepThreeAccepted(oid: string): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/report/application-step-three-accepted`;
        return this.http.get(url,
                  {
                    params: new HttpParams()
                    .set('oid', oid.toString()),
                    headers: getHttpHeaders(), 
                    observe: 'response',
                    responseType: 'blob'            
                  });
      
      }
    
}
