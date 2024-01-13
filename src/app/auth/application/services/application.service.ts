import { HttpClient, HttpHeaders, HttpParams, HttpResponse, HttpEvent } from '@angular/common/http';
import { Inject, Injectable, Optional } from '@angular/core';
import { CustomHttpUrlEncodingCodec } from '../model/encoder';
import {getHttpHeaders} from '@app/common/constants/constants';
import {resourceServerUrl} from '@app/common/constants/server-settings';
import {Observable} from 'rxjs';

import { ApplicationCustomsStepFive } from '../model/applicationCustomsStepFive';
import { ApplicationCustomsStepOne } from '../model/applicationCustomsStepOne';
import { ApplicationCustomsStepTwo } from '../model/applicationCustomsStepTwo';
import { Application } from '../model/application';

import { BASE_PATH, COLLECTION_FORMATS } from '../model/variables';
import { Configuration } from '../model/configuration';

@Injectable({
    providedIn: 'root'
})
export class ApplicationService {

    protected basePath = 'https://localhost:8081/api';
    public defaultHeaders = new HttpHeaders();
    public configuration = new Configuration();

    constructor(private http: HttpClient, @Optional()@Inject(BASE_PATH) basePath: string, @Optional() configuration: Configuration) {
        if (basePath) {
            this.basePath = basePath;
        }
        if (configuration) {
            this.configuration = configuration;
            this.basePath = basePath || configuration.basePath || this.basePath;
        }
    }

    /**
     * @param consumes string[] mime-types
     * @return true: consumes contains 'multipart/form-data', false: otherwise
     */
     private canConsumeForm(consumes: string[]): boolean {
        const form = 'multipart/form-data';
        for (const consume of consumes) {
            if (form === consume) {
                return true;
            }
        }
        return false;
    }


    // public saveApplicationCustomsStepOneUsingPOST(
    //     authorization: string, requestId: string, requestTime: string, requestTimeoutInSeconds: string, 
    //     applicationCustomsStepOneDto: ApplicationCustomsStepOne, 
    //     name?: string, observe: any = 'body', reportProgress: boolean = false 
    //     ): Observable<any> {

    //     if (authorization === null || authorization === undefined) {
    //         throw new Error('Required parameter authorization was null or undefined when calling saveApplicationCustomsStepOneUsingPOST.');
    //     }

    //     if (requestId === null || requestId === undefined) {
    //         throw new Error('Required parameter requestId was null or undefined when calling saveApplicationCustomsStepOneUsingPOST.');
    //     }

    //     if (requestTime === null || requestTime === undefined) {
    //         throw new Error('Required parameter requestTime was null or undefined when calling saveApplicationCustomsStepOneUsingPOST.');
    //     }

    //     if (requestTimeoutInSeconds === null || requestTimeoutInSeconds === undefined) {
    //         throw new Error('Required parameter requestTimeoutInSeconds was null or undefined when calling saveApplicationCustomsStepOneUsingPOST.');
    //     }

    //     if (applicationCustomsStepOneDto === null || applicationCustomsStepOneDto === undefined) {
    //         throw new Error('Required parameter applicationCustomsStepOneDto was null or undefined when calling saveApplicationCustomsStepOneUsingPOST.');
    //     }


    //     let queryParameters = new HttpParams({encoder: new CustomHttpUrlEncodingCodec()});
    //     if (name !== undefined && name !== null) {
    //         queryParameters = queryParameters.set('name', <any>name);
    //     }

    //     let headers = this.defaultHeaders;
    //     if (authorization !== undefined && authorization !== null) {
    //         headers = headers.set('Authorization', String(authorization));
    //     }
    //     if (requestId !== undefined && requestId !== null) {
    //         headers = headers.set('Request-Id', String(requestId));
    //     }
    //     if (requestTime !== undefined && requestTime !== null) {
    //         headers = headers.set('Request-Time', String(requestTime));
    //     }
    //     if (requestTimeoutInSeconds !== undefined && requestTimeoutInSeconds !== null) {
    //         headers = headers.set('Request-Timeout-In-Seconds', String(requestTimeoutInSeconds));
    //     }

    //     // to determine the Accept header
    //     let httpHeaderAccepts: string[] = [
    //         '*/*'
    //     ];
    //     const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
    //     if (httpHeaderAcceptSelected != undefined) {
    //         headers = headers.set('Accept', httpHeaderAcceptSelected);
    //     }

    //     // to determine the Content-Type header
    //     const consumes: string[] = [
    //         'application/json'
    //     ];
    //     const httpContentTypeSelected: string | undefined = this.configuration.selectHeaderContentType(consumes);
    //     if (httpContentTypeSelected != undefined) {
    //         headers = headers.set('Content-Type', httpContentTypeSelected);
    //     }

    //     return this.http.post<ApplicationCustomsStepOne>(`${this.basePath}/v1/application/customs/step-one`,
    //         applicationCustomsStepOneDto,
    //         {
    //             params: queryParameters,
    //             withCredentials: this.configuration.withCredentials,
    //             headers: getHttpHeaders(),
    //             observe: 'response',
    //             reportProgress: reportProgress
    //         }
    //     );
    // }



    saveApplicationCustomsStepOneUsingPOST(requestData: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/application/customs/step-one`;
        return this.http.post(url, requestData,
            {headers: getHttpHeaders(), observe: 'response'});

    }

    saveApplicationCustomsStepOneUsingPUT(requestData: any, oid: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/application/customs/step-one/${oid}`;
        return this.http.put(url, requestData,
            {headers: getHttpHeaders(), observe: 'response'});

    }

    getApplicationCustomsOid(id: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/application/${id}`;
        return this.http.get(url,
            {
                headers: getHttpHeaders(), observe: 'response'
            });
    }

    getApplicationCustomsStepOneOid(id: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/application/${id}`;
        return this.http.get(url,
            {
                headers: getHttpHeaders(), observe: 'response'
            });
    }

    getApplicationCustomsStepTwoOid(id: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/application/customs/step-two/${id}`;
        return this.http.get(url,
            {
                headers: getHttpHeaders(), observe: 'response'
            });
    }

    updateApplicationCustomsStepTwoUsingPUT(requestData: any, id: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/application/customs/step-two/${id}`;
        return this.http.put(url, requestData,
            {headers: getHttpHeaders(), observe: 'response'});
    }

    getOfficeList(): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/api/v1/office`;
        return this.http.get(url, {
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    saveApplicationCustomsStepFiveUsingPUT(requestData: any, id: any): Observable<any> {
        const url: string = `${resourceServerUrl}/v1/application/customs/step-five/${id}`;
        return this.http.put(url, requestData,
            {headers: getHttpHeaders(), observe: 'response'});
    }

    getOperatorWiseApplicationList(operatorName: any): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/application/search/operator/${operatorName}`;
        return this.http.get(url, {
            params: new HttpParams()
                .set('operatorName', operatorName.toString()),
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    getOperatorWiseApplicationListofSattlement(operatorName: any): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/application/search/operator/withSattlement/${operatorName}`;
        return this.http.get(url, {
            params: new HttpParams()
                .set('operatorName', operatorName.toString()),
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    getStatusWiseApplicationList(applicationStatus: any, applicationDate: string, userId: string): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/application/search`;
        return this.http.get(url, {
            params: new HttpParams()
                .set('applicationStatus', applicationStatus.toString())
                .set('userId', userId.toString())
                .set('applicationDate', applicationDate.toString()),
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    getStatusWiseApplicationListOfSattlement(applicationStatus: any, applicationDate: string, userId: string): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/application/searchWithOutSattlement`;
        return this.http.get(url, {
            params: new HttpParams()
                .set('applicationStatus', applicationStatus.toString())
                .set('userId', userId.toString())
                .set('applicationDate', applicationDate.toString()),
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    applicationStepFourReport(oid: string): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/report/customs-application-step-four`;
        
        return this.http.get(url,
                  {
                    params: new HttpParams()
                    .set('oid', oid.toString()),
                    headers: getHttpHeaders(), 
                    observe: 'response',
                    responseType: 'blob'            
                  });
      }

      getBillOfEntryNoList(billOfEntryNo: string): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/application/verify/${billOfEntryNo}`;

        return this.http.get(url, {
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    getBillOfentryIfExists(billOfEntryNo: string, officeName: string, date: string): Observable<HttpResponse<any>> {
        const url: string = `${resourceServerUrl}/v1/application/verify/${billOfEntryNo}/${officeName}/${date}`;

        return this.http.get(url, {
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    getUserProfileInfo(): Observable<HttpResponse<any>> {
        return this.http.get(`${resourceServerUrl}/v1/user`, {
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

    getMyProfile(oid: any): Observable<any> {
        const url: string = `${resourceServerUrl}/public/v1/userprofile/${oid}`;
        return this.http.get(url, {
            headers: getHttpHeaders(),
            observe: 'response'
        });
    }

}
