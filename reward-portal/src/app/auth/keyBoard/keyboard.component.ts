import { Component, OnInit } from "@angular/core";
import { Location } from '@angular/common';
import { Router } from "@angular/router";

@Component({
    templateUrl: './keyboard.component.html',
    styleUrls: ['./keuboard.component.css']
})



export class Keyboar implements OnInit{

    constructor(
        private _location: Location
    ){}

    ngOnInit(): void {
        
    }

    backClicked() {
        this._location.back();
      }


}