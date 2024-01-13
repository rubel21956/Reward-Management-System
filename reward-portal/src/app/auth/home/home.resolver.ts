import {catchError} from 'rxjs/operators';
import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {forkJoin, Observable, of} from 'rxjs';

import {HomeComponent} from './home.component';
import {HeaderService} from '@app/common/components/header/header.service';
import {SessionService} from '@app/common/services/session.service';
import { SharedService } from '@app/common/services/shared.service';

@Injectable({providedIn: 'root'})
export class HomeResolver implements Resolve<HomeComponent> {
    constructor(private headerService: HeaderService,
                private sessionService: SessionService,
                private sharedService: SharedService
                ) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> {

        return forkJoin(this.headerService.getUserProfileInfo()).pipe(catchError((error: any) => {
            // console.error(error.message);
            this.sessionService.logout();
            return of(null);
        }));
    }
}
