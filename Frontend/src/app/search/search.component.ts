import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  @Output() onSearch: EventEmitter<string> = new EventEmitter<string>();
  searchText?: string;
  search() {
    this.onSearch.emit(this.searchText);
  }

  constructor() {}

  ngOnInit(): void {}
}
