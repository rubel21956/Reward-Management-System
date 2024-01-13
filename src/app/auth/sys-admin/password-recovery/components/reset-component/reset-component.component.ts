import { Component, OnInit } from '@angular/core';

import { HttpClient, HttpParams } from '@angular/common/http';
import { resourceServerUrl } from '@app/common/constants/server-settings';
import { Observable } from 'rxjs';
import { forgetInfo } from './profileInfo';
import { MessageService } from 'primeng/api';
import { getHttpHeaders } from '@app/common/constants/constants';


@Component({
  selector: 'app-reset-component',
  templateUrl: './reset-component.component.html',
  styleUrls: ['./reset-component.component.css']
})
export class ResetComponentComponent implements OnInit {

  constructor(private http: HttpClient, public messageService: MessageService) { }

  public forgetInfo: forgetInfo;
  public userData: any = [];
  public LoginDetails = [];
  public userDetails = [];
  public isLoading = false;
  public confirmModal = false;
  public confirmModal2 = false;

  private passwordRecoveryURI: string = `${resourceServerUrl}/public/v1/send-email`;
  ngOnInit(): void {
    console.log("This is my Profile ");     
    this.getLoginData();
  }



  private getLoginData(){
    this.getLoginDetails().subscribe(
      (res)=>{
        this.isLoading = true;
        this.LoginDetails = res.body;
        console.log(this.LoginDetails);
        
      },
      (err)=>{
        this.isLoading = false;
        console.log(err);        
      },
      ()=>{
        this.getProfileDetails().subscribe(
          (res)=>{
            this.userDetails = res.body;
          },
          (err)=>{
            this.isLoading = false;
          },
          ()=>{
            var id;
            var name;
            var officeName;
            var status;
            var resetRequired;
            var reset;
            this.LoginDetails.forEach((entry)=>{
              if(entry.reset){
                id = entry.loginOid;
                status = entry.status;
                resetRequired = entry.resetRequired;
                reset = entry.reset;
                this.userDetails.forEach((profile)=>{
                 if(profile.loginOid == id){
                  name = profile.name;
                  officeName = profile.email;
                  this.userData.push(new forgetInfo(name,officeName,status,id,resetRequired, reset));
                 }
                }                
                ); 
              }             
            });  
            this.isLoading = false;   
          }
        );
      }
    );
  }



 private getLoginDetails(): Observable<any> {
  const url: string = `${resourceServerUrl}/v1/user/AllLogin`;
  return this.http.get(url, {
      headers: getHttpHeaders(),
      observe: 'response'
  });
}

private getProfileDetails(oid?: string): Observable<any> {
const url: string = `${resourceServerUrl}/public/v1/userprofile`;
return this.http.get(url, {
    headers: getHttpHeaders(),
    observe: 'response'
});
}

public sendEmailToUser(userId: string){
  this.isLoading = true;
this.isLoading = true;
this.sendEmail(userId).subscribe(
  (res)=>{
    console.log(res);
  }, 
  (err)=>{
    this.isLoading = false;
    console.log(err);}, 
    ()=>{
    this.isLoading = false;    
    this.confirmModal = true;
  }
);
}

private sendEmail(userId: string): Observable<any> {
return this.http.get(this.passwordRecoveryURI, 
 { params: new HttpParams()
  .set('oid', userId.toString()),
  headers: getHttpHeaders(), 
  observe: 'response' });
}

public pageReload(){
  window.location.reload();
}

}
