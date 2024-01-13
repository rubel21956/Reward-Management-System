import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SharedService} from '@app/common/services/shared.service';
import {Table} from 'primeng/table';
import {
    ApexAxisChartSeries,
    ApexChart,
    ApexDataLabels,
    ApexFill,
    ApexGrid,
    ApexLegend,
    ApexMarkers,
    ApexNonAxisChartSeries,
    ApexOptions,
    ApexPlotOptions,
    ApexResponsive,
    ApexStroke,
    ApexTitleSubtitle,
    ApexXAxis,
    ApexYAxis,
    ChartComponent,
} from 'ng-apexcharts';


import {ActivatedRoute, Route, Router} from '@angular/router';
import {MessageService} from 'primeng/api';
import {DashboardService} from '@app/auth/dashboard/dashboard.service';
import {Office} from '@app/auth/office/model/office';
import {FormBuilder, FormGroup} from '@angular/forms';
import { NbrApplicationService } from '../nbr-admin/services/nbr-application.service';

// export type ChartOptions = {
//     series: ApexNonAxisChartSeries;
//     chart: ApexChart;
//     labels: string[];
//     plotOptions: ApexPlotOptions;
//     legend: ApexLegend;
//     options: ApexOptions;
// };


/* The below chart code is written by Arif Hoosain*/

export type ChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  responsive: ApexResponsive[];
  labels: any;
  title: any;
  colors : any,
  dataLabels: any
  background: any,
  dropShadow: any,
  
  
};


export type ChartOptionsDonut = {
    series: ApexNonAxisChartSeries;
    chart: ApexChart;
    responsive: ApexResponsive[];
    labels: any;
    title: any;
    colors: any
  };



/* Chart code end */



export type ChartOptions1 = {
    series: ApexAxisChartSeries;
    chart: ApexChart;
    dataLabels: ApexDataLabels;
    plotOptions: ApexPlotOptions;
    responsive: ApexResponsive[];
    xaxis: ApexXAxis;
    legend: ApexLegend;
    fill: ApexFill;
    options: ApexOptions;
};

export type ChartOptions2 = {
    series: ApexAxisChartSeries;
    chart: ApexChart;
    xaxis: ApexXAxis;
    stroke: ApexStroke;
    dataLabels: ApexDataLabels;
    markers: ApexMarkers;
    tooltip: any; // ApexTooltip;
    yaxis: ApexYAxis;
    grid: ApexGrid;
    legend: ApexLegend;
    title: ApexTitleSubtitle;
    options: ApexOptions;
};

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

    private sessionList: any;
    public totalApplications: any;
    public totalApplicationsPrice: any;
    public totalAccepted: any;
    public totalAcceptedPrice: any;
    public totalReceived: any;
    public totalReceivedPrice: any;
    public totalDiscarded: any;
    public totalDiscardedPrice: any;
    public totalReturned: any;
    public totalReturnedPrice: any;


    @ViewChild('chart') chart: ChartComponent;
    public chartOptions: Partial<ChartOptions>;
    public chartOptionsDonut:Partial<ChartOptions>;
    // public chartOptions1: Partial<ChartOptions1>;
    // public chartOptions2: Partial<ChartOptions2>;

    // temp1: any = 12;
    // temp2: any = 20;
    // temp3: any = 22;
    // ans: number;

    // todaydate: any;
     public isLoading: boolean = false;
    // officeList: Office[];
    // requestTypeList: any[];
    // requestType: string = 'CREATION';
    // barChartTitle: string = '';
    // dashboardGroup: FormGroup;
    // public isLoading: boolean;
    // public isProgressBarLoading: boolean;
    // public sessionList: any[];
    // public sessionList1: any[];
    // public sessionList2: any[];
    // public rowsPerPageOptions: any[] = [5, 10, 20];
    // @ViewChild('sessionTableRef') sessionTableRef: Table;
    // public trDisabled: boolean = false;
    // public showAllList: boolean = true;

    // public rows: number = 5;
    // public defaultSearchText: string = '';
    // @ViewChild('sessionSearchPopRef') sessionSearchPopRef: ElementRef;
    // public totalRecords: number = 0;
    // public role: string = '';
    // public resetRequired: string = '';
    // public umsRole: string = '';
    // public currentOffice: string = '';
    // private defaultOffset: number = 0;
    // private defaultLimit: number = this.rows;
    // private chart1Label1: string = 'Active User: ';
    // private chart1Label2: string = 'Inactive User: ';
    // private chart1Label3: string = 'Request for creation: ';

    constructor(private fb: FormBuilder, private sharedService: SharedService, private dashboardService: DashboardService,
                private router: Router, private activateRoute: ActivatedRoute, private messageService: MessageService,
                private nbrApplicationService: NbrApplicationService, private route: ActivatedRoute
                ) {

    //     this.requestTypeList = [
    //         {name: 'AW User Creation', oid: 'CREATION'},
    //         {name: 'AW User Deactivation', oid: 'DEACTIVATION'},
    //         {name: 'AW User Transfer', oid: 'TRANSFER'},
    //         {name: 'AW User Reactivation', oid: 'REACTIVATION'}
    //     ];
    
}

    

    ngOnInit(): void {
            
      if(localStorage.getItem('resetRequired')=='Yes'){
        localStorage.removeItem('resetRequired');
        this.router.navigate(['/change-password']);
        return;
      }
      if(this.sharedService.getRmsRole() == "role-operator" || this.sharedService.getRmsRole() == "role-nbr-investigation"){
        this.route.data
        .subscribe(data => {
                               
            // if (data.home[0]) {                                
            //   if (data.home[0].body.resetRequired === 'Yes') {
            //     this.router.navigate(['/change-password']);
            //     return
            // }else{
            //   this.router.navigateByUrl("/application-customs-list");
            //   return;
            // }
            // }
            console.log(data);
            this.router.navigate(["/application-customs-list"]);
            return;
            
          });
            
      }else if(this.sharedService.getRmsRole() == "role-sys-admin"){
        this.router.navigate(["/operator-list"]);
        return;       
      }else if(this.sharedService.getRmsRole() == "role-nbr-admin"){
      

    //     this.umsRole = this.sharedService.getRmsRole();
    //     this.currentOffice = this.sharedService.getCurrentOfficeName();

    //     // this.getActiveUserList();
    //     // this.getDeactiveUserList();
    //     // this.getPendingUserList();
    //     this.getPieChartCountList();
    //     this.getBarChartCountList();
    //     this.getLineChartCountList();


    //     this.chartOptions = {
    //         options: {
    //             colors: ['#5CD0EC', '#F39E37', '#806AFE']
    //         },
    //         legend: {
    //             show: true,
    //             fontSize: '12px',
    //             fontWeight: 'bolder'
    //         },
    //         series: [this.temp1, this.temp2, this.temp3],
    //         chart: {
    //             height: 320,
    //             type: 'radialBar',
    //         },
    //         plotOptions: {
    //             radialBar: {
    //                 dataLabels: {
    //                     name: {
    //                         fontSize: '22px'
    //                     },
    //                     value: {
    //                         fontSize: '16px'
    //                     },
    //                     total: {
    //                         show: true,
    //                         label: 'Total',
    //                         formatter(w) {
    //                             return "56";
    //                         }
    //                     },
    //                 }
    //             },
    //         },
    //         labels: [this.chart1Label1, this.chart1Label2, this.chart1Label3]
    //     };

    //     this.chartOptions1 = {
    //         series: [
    //             {
    //                 name: 'Active AW User',
    //                 data: [24, 20, 21, 19, 12, 29]
    //             },
    //             {
    //                 name: 'Deactive AW User',
    //                 data: [13, 23, 20, 8, 13, 27]
    //             },
    //             {
    //                 name: 'Pending AW User',
    //                 data: [11, 17, 15, 15, 21, 14]
    //             }
    //         ],
    //         chart: {
    //             type: 'bar',
    //             height: 300,
    //             stacked: true,
    //             toolbar: {
    //                 show: false
    //             },
    //             zoom: {
    //                 enabled: true
    //             }
    //         },
    //         responsive: [
    //             {
    //                 breakpoint: 480,
    //                 options: {
    //                     legend: {
    //                         position: 'bottom',
    //                         offsetX: -10,
    //                         offsetY: 0
    //                     }
    //                 }
    //             }
    //         ],
    //         plotOptions: {
    //             bar: {
    //                 horizontal: false
    //             },
    //         },
    //         xaxis: {
    //             type: 'category',
    //             categories: [
    //                 'Sun',
    //                 'Mon',
    //                 'Tue',
    //                 'Wed',
    //                 'Thu'
    //             ]
    //         },
    //         legend: {
    //             position: 'right',
    //             offsetY: 40,
    //             fontWeight: 'bolder'
    //         },
    //         fill: {
    //             opacity: 1,
    //             colors: ['#5CD0EC', '#F39E37', '#806AFE']
    //         }
    //     };

    //     this.chartOptions2 = {
    //         options: {
    //             colors: ['#5CD0EC', '#F39E37', '#806AFE']
    //         },
    //         series: [
    //             {
    //                 name: 'Active AW User',
    //                 data: [25, 32, 18, 14, 13, 16, 11]
    //             },
    //             {
    //                 name: 'Deactive AW User',
    //                 data: [15, 21, 22, 12, 13, 18, 29]
    //             },
    //             {
    //                 name: 'Pending AW User',
    //                 data: [47, 37, 34, 49, 25, 18, 22]
    //             }
    //         ],
    //         chart: {
    //             height: 350,
    //             type: 'line',
    //             toolbar: {
    //                 show: false
    //             },
    //         },
    //         dataLabels: {
    //             enabled: false
    //         },
    //         stroke: {
    //             width: 5,
    //             curve: 'straight',
    //             dashArray: [0, 0, 0]
    //         },
    //         title: {
    //             text: '',
    //             align: 'left'
    //         },
    //         legend: {
    //             tooltipHoverFormatter(val, opts) {
    //                 return (
    //                     val +
    //                     ' - <strong>' +
    //                     opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex] +
    //                     '</strong>'
    //                 );
    //             }
    //         },
    //         markers: {
    //             size: 0,
    //             hover: {
    //                 sizeOffset: 6
    //             }
    //         },
    //         xaxis: {
    //             labels: {
    //                 trim: false
    //             },
    //             categories: [
    //                 '23 Mar',
    //                 '24 Mar',
    //                 '25 Mar',
    //                 '26 Mar',
    //                 '27 Mar',
    //                 '28 Mar',
    //                 '29 Mar',
    //             ]
    //         },
    //         tooltip: {
    //             y: [
    //                 {
    //                     title: {
    //                         formatter(val) {
    //                             return val + ' (mins)';
    //                         }
    //                     }
    //                 },
    //                 {
    //                     title: {
    //                         formatter(val) {
    //                             return val + ' per session';
    //                         }
    //                     }
    //                 },
    //                 {
    //                     title: {
    //                         formatter(val) {
    //                             return val;
    //                         }
    //                     }
    //                 }
    //             ]
    //         },
    //         grid: {
    //             borderColor: '#BDBDBD'
    //         }
    //     };
    // }

    // selectRequestType() {
    //     this.getBarChartCountList();
    // }

    // private getPieChartCountList(offset: number = this.defaultOffset, limit: number = this.defaultLimit) {
    //     this.dashboardService.getPieChartCount(this.umsRole, this.currentOffice, offset, limit).subscribe(res => {
    //             if (res.status === 200) {
    //                 this.temp1 = res.body.activeCount;
    //                 this.temp2 = res.body.deactivatedCount;
    //                 this.temp3 = res.body.pendingCount;
    //                 this.ans = (+this.temp1) + (+this.temp2) + (+this.temp3);
    //                 let totalCount = this.ans.toString();
    //                 this.chart1Label1 += this.temp1;
    //                 this.chart1Label2 += this.temp2;
    //                 this.chart1Label3 += this.temp3;
    //                 this.chartOptions = {
    //                     options: {
    //                         colors: ['#5CD0EC', '#F39E37', '#806AFE']
    //                     },
    //                     legend: {
    //                         show: true,
    //                         fontSize: '12px',
    //                         fontWeight: 'bolder'
    //                     },
    //                     series: [Math.round((this.temp1/this.ans)*100),
    //                         Math.round((this.temp2/this.ans)*100),
    //                         Math.round((this.temp3/this.ans)*100)],
    //                     chart: {
    //                         height: 320,
    //                         type: 'radialBar',
    //                     },
    //                     plotOptions: {
    //                         radialBar: {
    //                             dataLabels: {
    //                                 name: {
    //                                     fontSize: '22px'
    //                                 },
    //                                 value: {
    //                                     fontSize: '16px'
    //                                 },
    //                                 total: {
    //                                     show: true,
    //                                     label: 'Total',
    //                                     formatter(w) {
    //                                         return totalCount;
    //                                     }
    //                                 },
    //                             }
    //                         },
    //                     },
    //                     labels: [this.chart1Label1, this.chart1Label2, this.chart1Label3]
    //                 };
    //             }
    //         },
    //         err => {
    //             this.isProgressBarLoading = false;
    //             this.isLoading = false;
    //             if (err.status === 404) {
    //                 this.totalRecords = 0;
    //             }

    //             if (err.error && err.error.message) {
    //                 this.messageService.add({severity: 'error', summary: err.error.message, detail: ''});
    //             }
    //         },
    //         () => {
    //             this.isProgressBarLoading = false;
    //             this.isLoading = false;
    //         });
    // }

    // private getLineChartCountList(offset: number = this.defaultOffset, limit: number = this.defaultLimit) {
    //     this.dashboardService.getLineChartCount(this.umsRole, this.currentOffice, this.requestType).subscribe(res => {
    //             if (res.status === 200) {
    //                 let chartName = 'Active AW User';
    //                 let creationRequestCount = res.body.creationCount;
    //                 let deactivationRequestCount = res.body.deactivationCount;
    //                 let transferRequestCount = res.body.transferCount;
    //                 let requestDates = res.body.dateList;

    //                 this.chartOptions2 = {
    //                     options: {
    //                         colors: ['#5CD0EC', '#F39E37', '#806AFE']
    //                     },
    //                     series: [
    //                         {
    //                             name: 'Creation Request',
    //                             data: creationRequestCount
    //                         },
    //                         {
    //                             name: 'Deactivation Request',
    //                             data: deactivationRequestCount
    //                         },
    //                         {
    //                             name: 'Transfer Request',
    //                             data: transferRequestCount
    //                         }
    //                     ],
    //                     chart: {
    //                         height: 350,
    //                         type: 'line',
    //                         toolbar: {
    //                             show: false
    //                         },
    //                     },
    //                     dataLabels: {
    //                         enabled: false
    //                     },
    //                     stroke: {
    //                         width: 5,
    //                         curve: 'straight',
    //                         dashArray: [0, 0, 0]
    //                     },
    //                     title: {
    //                         text: '',
    //                         align: 'left'
    //                     },
    //                     legend: {
    //                         tooltipHoverFormatter(val, opts) {
    //                             return (
    //                                 val +
    //                                 ' - <strong>' +
    //                                 opts.w.globals.series[opts.seriesIndex][opts.dataPointIndex] +
    //                                 '</strong>'
    //                             );
    //                         }
    //                     },
    //                     markers: {
    //                         size: 0,
    //                         hover: {
    //                             sizeOffset: 6
    //                         }
    //                     },
    //                     xaxis: {
    //                         labels: {
    //                             trim: false
    //                         },
    //                         categories: requestDates
    //                     },
    //                     tooltip: {
    //                         y: [
    //                             {
    //                                 title: {
    //                                     formatter(val) {
    //                                         return val + ':';
    //                                     }
    //                                 }
    //                             },
    //                             {
    //                                 title: {
    //                                     formatter(val) {
    //                                         return val + ':';
    //                                     }
    //                                 }
    //                             },
    //                             {
    //                                 title: {
    //                                     formatter(val) {
    //                                         return val + ':';
    //                                     }
    //                                 }
    //                             }
    //                         ]
    //                     },
    //                     grid: {
    //                         borderColor: '#BDBDBD'
    //                     }
    //                 };
    //             }
    //         },
    //         err => {
    //             this.isProgressBarLoading = false;
    //             this.isLoading = false;
    //             if (err.status === 404) {
    //                 this.totalRecords = 0;
    //             }

    //             if (err.error && err.error.message) {
    //                 this.messageService.add({severity: 'error', summary: err.error.message, detail: ''});
    //             }
    //         },
    //         () => {
    //             this.isProgressBarLoading = false;
    //             this.isLoading = false;
    //         });
    // }

    // private getBarChartCountList(offset: number = this.defaultOffset, limit: number = this.defaultLimit) {
    //     this.dashboardService.getBarChartCount(this.umsRole, this.currentOffice, this.requestType).subscribe(res => {
    //             if (res.status === 200) {
    //                 let chartName = 'Active AW User';
    //                 let chartCount = res.body.countList;
    //                 let requestDates = res.body.dateList;

    //                 this.chartOptions1 = {
    //                     series: [
    //                         {
    //                             name: chartName,
    //                             data: chartCount
    //                         }
    //                         // {
    //                         //     name: 'Deactive AW User',
    //                         //     data: [13, 23, 20, 8, 13, 27]
    //                         // },
    //                         // {
    //                         //     name: 'Pending AW User',
    //                         //     data: [11, 17, 15, 15, 21, 14]
    //                         // }
    //                     ],
    //                     chart: {
    //                         type: 'bar',
    //                         height: 300,
    //                         stacked: true,
    //                         toolbar: {
    //                             show: false
    //                         },
    //                         zoom: {
    //                             enabled: true
    //                         }
    //                     },
    //                     responsive: [
    //                         {
    //                             breakpoint: 480,
    //                             options: {
    //                                 legend: {
    //                                     position: 'bottom',
    //                                     offsetX: -10,
    //                                     offsetY: 0
    //                                 }
    //                             }
    //                         }
    //                     ],
    //                     plotOptions: {
    //                         bar: {
    //                             horizontal: false
    //                         },
    //                     },
    //                     xaxis: {
    //                         type: 'category',
    //                         categories: requestDates
    //                     },
    //                     legend: {
    //                         position: 'right',
    //                         offsetY: 40,
    //                         fontWeight: 'bolder'
    //                     },
    //                     fill: {
    //                         opacity: 1,
    //                         colors: ['#5CD0EC', '#F39E37', '#806AFE']
    //                     }
    //                 };
    //             }
    //         },
    //         err => {
    //             this.isProgressBarLoading = false;
    //             this.isLoading = false;
    //             if (err.status === 404) {
    //                 this.totalRecords = 0;
    //             }

    //             if (err.error && err.error.message) {
    //                 this.messageService.add({severity: 'error', summary: err.error.message, detail: ''});
    //             }
    //         },
    //         () => {
    //             this.isProgressBarLoading = false;
    //             this.isLoading = false;
    //         });
    // }

    // private getActiveUserList(offset: number = this.defaultOffset, limit: number = this.defaultLimit) {
    //     this.dashboardService.getActiveUserList(this.umsRole, this.currentOffice, offset, limit).subscribe(res => {
    //             if (res.status === 200) {
    //                 // this.sessionList = res.body.data
    //                 this.totalRecords = res.body.totalRecords
    //                 this.temp1 = this.totalRecords;
    //             }
    //         },
    //         err => {
    //             this.isProgressBarLoading = false;
    //             this.isLoading = false;
    //             if (err.status === 404) {
    //                 this.totalRecords = 0;
    //             }

    //             if (err.error && err.error.message) {
    //                 this.messageService.add({severity: 'error', summary: err.error.message, detail: ''});
    //             }
    //         },
    //         () => {
    //             this.isProgressBarLoading = false;
    //             this.isLoading = false;
    //         });
    // }

    // private getDeactiveUserList(offset: number = this.defaultOffset, limit: number = this.defaultLimit) {
    //     this.dashboardService.getDeactiveUserList(this.umsRole, this.currentOffice, offset, limit).subscribe(res => {
    //             if (res.status === 200) {
    //                 // this.sessionList1 = res.body.data
    //                 this.totalRecords = res.body.totalRecords
    //                 this.temp2 = this.totalRecords
    //             }
    //         },
    //         err => {
    //             this.isProgressBarLoading = false;
    //             this.isLoading = false;
    //             if (err.status === 404) {
    //                 // this.sessionList1 = [];
    //                 this.totalRecords = 0;
    //             }

    //             if (err.error && err.error.message) {
    //                 this.messageService.add({severity: 'error', summary: err.error.message, detail: ''});
    //             }
    //         },
    //         () => {
    //             this.isProgressBarLoading = false;
    //             this.isLoading = false;
    //         });
    // }

    // private getPendingUserList(offset: number = this.defaultOffset, limit: number = this.defaultLimit) {
    //     if (this.sharedService.getRmsRole() === 'OfficeAdmin') {
    //         this.currentOffice = this.sharedService.getCurrentOfficeName();
    //     }
    //     this.dashboardService.getPendingUserList(this.umsRole, this.currentOffice, offset, limit).subscribe(res => {
    //             if (res.status === 200) {
    //                 // this.sessionList2 = res.body.data
    //                 this.totalRecords = res.body.totalRecords
    //                 this.temp3 = this.totalRecords
    //             }
    //         },
    //         err => {
    //             this.isProgressBarLoading = false;
    //             this.isLoading = false;
    //             if (err.status === 404) {
    //                 // this.sessionList2 = [];
    //                 this.totalRecords = 0;
    //             }

    //             if (err.error && err.error.message) {
    //                 this.messageService.add({severity: 'error', summary: err.error.message, detail: ''});
    //             }
    //         },
    //         () => {
    //             this.isProgressBarLoading = false;
    //             this.isLoading = false;
    //         });
    // }


        /** The below section is created by Arif Hossain **/        
        
        this.getApplicantList();
        this.chartOptions = {
          series: [0,0,0,0],           
          chart: {
            width: 500,
            type: "pie"             
          },
          
          title: {
            title: {
              text: "টাকার পরিমাণ",
              align: 'middle',
              margin: 10,
              offsetX: -42,
              offsetY: 10,
              floating: false,
              style: {
                fontSize:  '20px',
                fontWeight:  'bold',
                fontFamily:  'src/assets/fonts/kalpurush/kalpurush.ttf',
                color:  '#263238'
                
            },
          },
          },   
          
          colors: ['#0000FF','#228B22', '#b60000'],                  
          labels: [],  
          dataLabels: {
            enabled: false // Set this to false to disable the data labels
        },                                 
        };


        this.chartOptionsDonut = {
          series: [0, 0, 0, 0],
          chart: {
            type: "pie",
            width: 550,            
          },
          title: {
            text: "",
            align: 'left',
            margin: 10,
            offsetX: 0,
            offsetY: 0,
            floating: false,
            style: {
              fontSize:  '14px',
              fontWeight:  'bold',
              fontFamily:  undefined,
              color:  '#263238'
              
          },
        },
        colors: ['#0000FF','#228B22', '#b60000', '#581845'],                  
        labels: ["কার্যক্রম চলমান","মঞ্জুরীর জন্য সুপারিশকৃত","বিবেচনাযোগ্য নয়","ফেরতকৃত"],
        dataLabels: {
          enabled: false // Set this to false to disable the data labels
      }, 
      
        
          
        }



      }

         ///////* The below chart code is written by Arif Hossain *///////       
         ///////////////////////*End chart code *////////////////////////
       
}

private chartValueofObserveable(totalAccepted: number, totalReceived: number, totalRejected: number, totalReturned: number,
                                totalAcceptedPrice: number, totalReceivedPrice: number, totalDiscardedPrice: number, totalReturnedPrice: number){
       ///////* The below chart code is written by Arif Hossain *///////     
       this.chartOptions = {
           series: [totalReceived,totalAccepted,totalRejected,totalReturned],           
           chart: {
             width: 450,
             type: "pie",
             offsetX: -40           
           },   
           title: {
            text: "আবেদনের সংখ্যা",
            align: 'middle',
            margin: 0,
            offsetX: -75,
            offsetY: 0,
            floating: false,
            style: {
              fontSize:  '20px',
              fontWeight:  'bold',
              fontFamily:  '',
              color:  '#263238'
          },
          
        },
           colors: ['#0000FF','#228B22', '#b60000', '#581845'],                             
           labels: ["কার্যক্রম চলমান","মঞ্জুরীর জন্য সুপারিশকৃত","বিবেচনাযোগ্য নয়","ফেরতকৃত"], 
          
// dataLabels: {
//   style: {
//     fontSize: '12px',
//     fontWeight: 'bold',
//   },
//   background: {
//     enabled: false,
//     foreColor: '#fff',
//     borderRadius: 2,
//     padding: 4,
//     opacity: 0.9,
//     borderWidth: 1,
//     borderColor: '#fff'
//   },
  
// },   
          
                        
                              
         };

         this.chartOptionsDonut = {
          series: [totalReceivedPrice,totalAcceptedPrice,totalDiscardedPrice,totalReturnedPrice],
          chart: {
            type: "pie",
            width: 450,
            offsetX: -40
          },
          title: {
            text: "অর্থের পরিমাণ",
            align: 'middle',
            margin: 0,
            offsetX: -82,
            offsetY: 0,
            floating: false,
            style: {
              fontSize:  '20px',
              fontWeight:  'bold',
              fontFamily:  '',
              color:  '#263238'
          },
        },
        colors: ['#0000FF','#228B22', '#b60000', '#581845'],
        labels: ["কার্যক্রম চলমান","মঞ্জুরীর জন্য সুপারিশকৃত","বিবেচনাযোগ্য নয়","ফেরতকৃত"], 
        dataLabels: {
          enabled: false // Set this to false to disable the data labels
      }, 
        };
       
         ///////////////////////*End chart code *////////////////////////
   
}    

    funtionForFiltering(array: any, name: string): number{
        let num = 0;
        for (let i = 0; i < array.length; i++) {
            if(array[i].applicationCustomsStepOneDto.applicationStatusBn == name){
                num++;                                
            }        
        }
        return num;
    }
    functionForTotalPrice(array: any, name:any): number{
        let num = 0;
        for (let j = 0; j < array.length; j++){
            if(array[j].applicationCustomsStepOneDto.applicationStatusBn == name){
                num += array[j].applicationCustomsStepOneDto.rewardableAmount;                                
            }else if(name == ""){                                
                num += array[j].applicationCustomsStepOneDto.rewardableAmount;
            }          
        }   
        console.error(num);
                     
        return num;
    }

private getApplicantList(): void {
    this.isLoading = true;
    this.nbrApplicationService.getApplicationList().subscribe(res => { 
        this.totalAccepted = 12;       
            if (res.status === 200) {
                console.log(res);                
                this.totalApplications = res.body.length;
                this.totalReceived = this.funtionForFiltering(res.body, "কার্যক্রম চলমান");
                this.totalAccepted = this.funtionForFiltering(res.body, "মঞ্জুরীর জন্য সুপারিশকৃত");
                this.totalDiscarded = this.funtionForFiltering(res.body, "বিবেচনাযোগ্য নয়");
                this.totalReturned = this.funtionForFiltering(res.body, "ফেরতকৃত");
                this.totalApplicationsPrice = this.functionForTotalPrice(res.body, "");
                this.totalReceivedPrice = this.functionForTotalPrice(res.body, "কার্যক্রম চলমান");                
                this.totalAcceptedPrice = this.functionForTotalPrice(res.body, "মঞ্জুরীর জন্য সুপারিশকৃত");
                this.totalDiscardedPrice = this.functionForTotalPrice(res.body, "বিবেচনাযোগ্য নয়");               
                
                this.totalReturnedPrice = this.functionForTotalPrice(res.body, "ফেরতকৃত");   
                console.log(this.totalReturnedPrice);                 
                this.chartValueofObserveable(this.totalAccepted,this.totalReceived,this.totalDiscarded, this.totalReturned,
                                             this.totalAcceptedPrice, this.totalReceivedPrice, this.totalDiscardedPrice, this.totalReturnedPrice);                                      
            }
        },
        err => {
            if (err.status === 404) {
                this.sessionList = [];
                this.isLoading = false;
            }
            if (err.error && err.error.message) {
            }
        },
        () => {
          this.isLoading = false;
          console.log(document.getElementById("apex2"));
          
        });
}

}
