import dayjs from 'dayjs/esm';

import { IMessage, NewMessage } from './message.model';

export const sampleWithRequiredData: IMessage = {
  id: 29027,
  userLogin: 'turn-key SQL Oklahoma',
  chatDate: dayjs('2022-10-28T06:08'),
  textMessage: 'hack Global Louisiana',
};

export const sampleWithPartialData: IMessage = {
  id: 57475,
  userLogin: 'action-items',
  chatDate: dayjs('2022-10-28T10:51'),
  textMessage: 'cross-platform',
};

export const sampleWithFullData: IMessage = {
  id: 68531,
  userLogin: 'quantify cyan',
  chatDate: dayjs('2022-10-28T08:32'),
  textMessage: 'turquoise copy',
};

export const sampleWithNewData: NewMessage = {
  userLogin: 'Rustic Dynamic Zimbabwe',
  chatDate: dayjs('2022-10-28T04:39'),
  textMessage: 'Small parsing Virginia',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
