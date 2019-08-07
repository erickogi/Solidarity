package com.dev.common.ui.auth


import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.common.data.FRAGMENTS_NAV_KEYS
import com.dev.common.listeners.ReplaceFragmentListener
import com.agriclinic.common.models.oauth.Oauth
import com.agriclinic.common.ui.auth.AuthViewModel
import com.dev.common.utils.Validator
import com.dev.common.utils.textWatchers.NameTextWatcher
import com.dev.common.R
import com.dev.common.models.association.Association
import com.dev.common.models.custom.SearchModel
import com.dev.common.models.custom.Status
import com.dev.common.models.sectors.Sector
import com.dev.common.utils.AccountActionsConstanr
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.SearchDialogListener
import com.dev.common.utils.textWatchers.PhoneTextWatcher
import com.dev.common.utils.viewUtils.ViewUtils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.onboard_register_names.*
import kotlinx.android.synthetic.main.toolback_bar.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class OnBoardReg1Fragment : Fragment(), View.OnClickListener {


    override fun onClick(p0: View?) {

        if (p0 == linear_back) {
            activity?.onBackPressed()
        }

        if (p0 == edt_association) {
            getOauth().profile?.association_id=null
            edt_association.setText("")


            if(Validator.isNull(edt_sector)) {
                getOauth().profile?.sector_id=null
                getOauth().profile?.association_id=null
                edt_association.setText("")
                edt_sector.setText("")
                return
            }
            viewModel.getAssociations(getOauth().profile?.sector_id)
        }
        if (p0 == edt_sector) {
            getOauth().profile?.sector_id=null
            getOauth().profile?.association_id=null
            edt_association.setText("")
            edt_sector.setText("")
            viewModel.getSectors()


        }
        if(p0==edt_dob){
            chooseDob()
        }
        if (p0 == btn_next) {

            if (Validator.isValidName(edt_first_name) && Validator.isValidName(edt_last_name)&&Validator.isValidPhone(edt_phone)&&Validator.isValidDateOfBirth(edt_dob)&&Validator.isSpinnerSelected(edt_gender)
                &&!Validator.isNull(edt_id)&&!Validator.isNull(edt_occupation)&&!Validator.isNull(edt_sector)&&!Validator.isNull(edt_association)
            ) {


                    verify()

            }
        }

    }


    private fun verify() {


        viewModel.saveProfile(getOauth())
         replaceFragmentListener.replace(
                    FRAGMENTS_NAV_KEYS.ONBOARD_REGISTER_1,
                    FRAGMENTS_NAV_KEYS.ONBOARD_NEW_PASSWORD, getOauth())


    }

    private fun getOauth(): Oauth {

        val oauth = viewModel.getProfile()
        oauth.profile?.first_name = Validator.getText(edt_first_name)
        oauth.profile?.last_name = Validator.getText(edt_last_name)
        oauth.profile?.phone_number = Validator.getPhoneNumber(edt_phone)
        oauth.profile?.gender =  Validator.getSpinerChecked(edt_gender)
        oauth.profile?.occupation =  Validator.getText(edt_occupation)
        oauth.profile?.dob =  Validator.getText(edt_dob)
        oauth.profile?.id_no =  Validator.getText(edt_id)
        oauth.profile?.accountAction = AccountActionsConstanr().createAccountAction




        return oauth

    }


    private lateinit var replaceFragmentListener: ReplaceFragmentListener

    companion object {
        fun newInstance() = OnBoardReg1Fragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        replaceFragmentListener = activity as ReplaceFragmentListener
    }

    private lateinit var viewModel: AuthViewModel
    var role = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.onboard_register_names, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        initWidgets()

        viewModel.observeSectors().observe(this, Observer {
            ViewUtils.setStatus(activity,view,it.status,it.message,false, ViewUtils.ErrorViewTypes.TOAST,it.exception)
            if(it.status== Status.SUCCESS){

                Log.d("sdsfs", Gson().toJson(it))

                if(it.data?.sectors != null){
                    sectors(it.data.sectors)
                }



            }
        })
        viewModel.observeAssociations().observe(this, Observer {
            ViewUtils.setStatus(activity,view,it.status,it.message,false, ViewUtils.ErrorViewTypes.TOAST,it.exception)
            if(it.status== Status.SUCCESS){

                if(it.data!=null&&it.data.associations!=null){
                    associations(it.data.associations)
                }



            }
        })

    }

    private fun sectors(data: List<Sector>?) {
        val <T> searchModels = ArrayList<SearchModel<Sector>>()


        Log.d("sdsfs", Gson().toJson(data))

        for (subcategories in data as List) {
            searchModels.add(
                SearchModel(
                    title = subcategories.name!!,
                    id = "" + subcategories.id!!,
                    serices = subcategories
                )
            )
        }
        CommonUtils().searchDialog(context!!, "Search...", "Search ",
            searchModels, true, object :
                SearchDialogListener<Sector> {
                override fun onResults(
                    item: SearchModel<Sector>?,
                    position: Int
                ) {
                    edt_sector.setText(item?.getServices()?.name)



                    val oauth = viewModel.getProfile()
                    oauth.profile?.sector = Validator.getText(edt_sector)
                    oauth.profile?.sector_id = item?.getServices()?.id

                    viewModel.saveProfile(oauth)


                }
            })

    }
    private fun associations(data: List<Association>?) {
        val <T> searchModels = ArrayList<SearchModel<Association>>()



        for (subcategories in data as List) {
            searchModels.add(
                SearchModel(
                    title = subcategories.name!!,
                    id = "" + subcategories.id!!,
                    serices = subcategories
                )
            )
        }
        CommonUtils().searchDialog(context!!, "Search...", "Search ",
            searchModels, true, object :
                SearchDialogListener<Association> {
                override fun onResults(
                    item: SearchModel<Association>?,
                    position: Int
                ) {
                    edt_association.setText(item?.getServices()?.name)



                    val oauth = viewModel.getProfile()
                    oauth.profile?.association = Validator.getText(edt_association)
                    oauth.profile?.association_id = item?.getServices()?.id

                    viewModel.saveProfile(oauth)


                }
            })

    }

    private fun initWidgets() {


        edt_first_name.requestFocus()
        btn_next.setOnClickListener(this)
        linear_back.setOnClickListener(this)
        edt_first_name.addTextChangedListener(NameTextWatcher(edt_first_name))
        edt_last_name.addTextChangedListener(NameTextWatcher(edt_last_name))
        edt_phone.addTextChangedListener(PhoneTextWatcher(edt_phone,txt_ke))

        edt_sector.setOnClickListener (this)
        edt_association.setOnClickListener (this)
        edt_dob.setOnClickListener (this)



        val profile=viewModel.getProfile()


        viewModel.saveProfile(profile)
    }

    internal val VERIFY_DATE_FORMAT = "yyyy-MM-dd"
    internal val VERIFY_GENDER_ARRAY = arrayOf("M", "F")


    private fun chooseDob() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val cal = Calendar.getInstance()
        val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, yeard, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, yeard)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat(
                VERIFY_DATE_FORMAT, Locale.US
            )
            edt_dob?.setText(sdf.format(cal.time))



        }, year, month, day)
        dpd.datePicker.minDate = Date().time
        dpd.show()

    }



}
