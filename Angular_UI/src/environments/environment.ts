// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  RESTURL: {
   SERVER:"http://localhost:8080/",
   LOGIN: 'login',
   EMPLOYEE: 'employees',
   ROLES: 'roles',
   DEPARTMENTS: 'departments',
   EMPLOYEE_NAMES:'employees/names',
   EMPLOYEE_ROLE: 'employees/role/',
   PROJECT: 'project',
   PROJECT_TEAM: 'project/team/',
   MAP_PROJECT:'project/map',
   UNMAP_PROJECT: 'project/unmap',
   LEAVE_TYPE: 'leaveType',
   REQUEST_LEAVE:'leave',
   LEAVE_BALANCE:'leaveBalance/',
   APPROVE_REJECT_LEAVE:'leave/updateStatus',
  },
  GENDER:['F', 'M'],
  LEAVE_STATUS:{
      1: 'PENDING',
      2: 'APPROVED',
      3: 'REJECTED'
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
