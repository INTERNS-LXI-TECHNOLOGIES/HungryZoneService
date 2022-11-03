import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FoodItemDetailComponent } from './food-item-detail.component';

describe('FoodItem Management Detail Component', () => {
  let comp: FoodItemDetailComponent;
  let fixture: ComponentFixture<FoodItemDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FoodItemDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ foodItem: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FoodItemDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FoodItemDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load foodItem on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.foodItem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
