import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FoodItemFormService } from './food-item-form.service';
import { FoodItemService } from '../service/food-item.service';
import { IFoodItem } from '../food-item.model';

import { FoodItemUpdateComponent } from './food-item-update.component';

describe('FoodItem Management Update Component', () => {
  let comp: FoodItemUpdateComponent;
  let fixture: ComponentFixture<FoodItemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let foodItemFormService: FoodItemFormService;
  let foodItemService: FoodItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FoodItemUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(FoodItemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FoodItemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    foodItemFormService = TestBed.inject(FoodItemFormService);
    foodItemService = TestBed.inject(FoodItemService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const foodItem: IFoodItem = { id: 456 };

      activatedRoute.data = of({ foodItem });
      comp.ngOnInit();

      expect(comp.foodItem).toEqual(foodItem);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFoodItem>>();
      const foodItem = { id: 123 };
      jest.spyOn(foodItemFormService, 'getFoodItem').mockReturnValue(foodItem);
      jest.spyOn(foodItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ foodItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: foodItem }));
      saveSubject.complete();

      // THEN
      expect(foodItemFormService.getFoodItem).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(foodItemService.update).toHaveBeenCalledWith(expect.objectContaining(foodItem));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFoodItem>>();
      const foodItem = { id: 123 };
      jest.spyOn(foodItemFormService, 'getFoodItem').mockReturnValue({ id: null });
      jest.spyOn(foodItemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ foodItem: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: foodItem }));
      saveSubject.complete();

      // THEN
      expect(foodItemFormService.getFoodItem).toHaveBeenCalled();
      expect(foodItemService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFoodItem>>();
      const foodItem = { id: 123 };
      jest.spyOn(foodItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ foodItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(foodItemService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
