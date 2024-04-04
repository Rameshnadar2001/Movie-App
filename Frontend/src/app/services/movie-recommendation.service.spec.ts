import { TestBed } from '@angular/core/testing';

import { MovieRecommendationService } from './movie-recommendation.service';

describe('MovieRecommendationService', () => {
  let service: MovieRecommendationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MovieRecommendationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
