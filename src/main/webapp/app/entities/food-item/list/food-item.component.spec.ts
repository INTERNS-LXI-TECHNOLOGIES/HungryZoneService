import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FoodItemService } from '../service/food-item.service';

import { FoodItemComponent } from './food-item.component';

describe('FoodItem Management Component', () => {
  let comp: FoodItemComponent;
  let fixture: ComponentFixture<FoodItemComponent>;
  let service: FoodItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'food-item', component: FoodItemComponent }]), HttpClientTestingModule],
      declarations: [FoodItemComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(FoodItemComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FoodItemComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FoodItemService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.foodItems?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to foodItemService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getFoodItemIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getFoodItemIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
