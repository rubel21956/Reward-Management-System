import { Component } from '@angular/core';
import { SwUpdate } from '@angular/service-worker';
import { PrimeNGConfig } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private swUpdate: SwUpdate, private config: PrimeNGConfig, private translateService: TranslateService) {
      
  }

  ngOnInit() {
    if (this.swUpdate.isEnabled) {
        this.swUpdate.available.subscribe(() => {
            if(confirm('New version available. Load New Version?')) {
                window.location.reload();
            }
        });
    } 
    this.translateService.setDefaultLang('en');      
  }

  translate(lang: string) {
    this.translateService.use(lang);
    this.translateService.get('primeng').subscribe(res => this.config.setTranslation(res));
    
  }
}
