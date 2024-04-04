import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css']
})
export class FilterComponent implements OnInit {
  @Output() onchipChange:EventEmitter<string>=new EventEmitter();
fValue: string='';

test(selectedChange:string) {
  this.fValue=selectedChange;
  this.onchipChange.emit(this.fValue)
}

  constructor() { }

  ngOnInit(): void {

  }

}
