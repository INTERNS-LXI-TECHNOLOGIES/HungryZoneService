import { IFoodItem, NewFoodItem } from './food-item.model';

export const sampleWithRequiredData: IFoodItem = {
  id: 55365,
  quandity: 37862,
  unit: 'payment Ball Liberian',
};

export const sampleWithPartialData: IFoodItem = {
  id: 89339,
  quandity: 1326,
  unit: 'calculate Industrial infomediaries',
};

export const sampleWithFullData: IFoodItem = {
  id: 4064,
  quandity: 74931,
  unit: 'Rustic Concrete',
};

export const sampleWithNewData: NewFoodItem = {
  quandity: 54344,
  unit: 'Cambridgeshire',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
