import {HttpBackend, HttpClient, HttpParams, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from '@src/environments/environment';
import { getHttpHeaders, getHttpHeadersForFile } from '../constants/constants';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class FileService {

    public withCredentials = false;

    constructor(private http: HttpClient, private handler: HttpBackend) {
        // this.http = new HttpClient(handler);
    }

    upload(files: Array<File>, url: string, formData: FormData, fileDir: string, name?: string) {
        if (!files) {
            return;
        }
        // if (environment.production) {
        //     this.withCredentials = true;
        // }
        const req = new HttpRequest('POST', url, formData, {
            params: new HttpParams()
            .set('fileDir', fileDir).set('fileName', name),
            
            headers: getHttpHeadersForFile(),
            reportProgress: true,
            withCredentials: this.withCredentials
        });
        return this.http.request(req);
    }

    deleteUplodedFile(name: string, fileDir: string): Observable<any> {
        console.log(name);        
        const url: string = `${name}`;
        return this.http.get(url,
            {
                params: new HttpParams()
                .set('fileDir', fileDir).set('fileName', name),
                headers: getHttpHeaders(), observe: 'response'
            });
    }

}
