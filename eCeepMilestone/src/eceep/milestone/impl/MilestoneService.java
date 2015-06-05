package eceep.milestone.impl;

import java.util.ArrayList;
import java.util.List;

import eceep.milestone.Leaf;
import eceep.milestone.Milestone;
import eceep.milestone.Step;

/**
 * Milestone:  Step0 --- Step3 --- Step5 --- Step7
 * --------------------------------------------------------------------------
 * Steps tree: Step0 -+- Step1 --- Step2
 *                    +- Step3 -+- Step4
 *                    |         +- Step5 --- Step7
 *                    +- Step6
 * --------------------------------------------------------------------------                                      
 */
public class MilestoneService<T extends Step> implements Milestone<T> {
	protected Leaf<T> theFirstLeaf;
	
	private List<T> milestones;
	private int index;
	
	private List<T> steps;
	
	public MilestoneService(){
		this.steps = new ArrayList<T>();
	}
	
	
	/* Get properties -------------------------------------------- */
	@Override
	public Leaf<T> getTheFirstLeaf() {
		return this.theFirstLeaf;
	}
	
	@Override
	public Leaf<T> getCurrentLeaf() {
		Leaf<T> ret = null;
		
		if (this.index >= 0 && this.index < milestones.size())
			ret = find(theFirstLeaf, milestones.get(this.index));
		
		return ret;
	}
	
	@Override
	public List<T> getSteps() {
		return this.steps;
	}
	
	@Override
	public List<T> getMilestones() {
		return this.milestones;
	}
	
	@Override
	public boolean isActive() {
		return this.index>=0 && this.index < this.milestones.size() && this.getCurrentLeaf().getStep().isActive();
	}
	/* ------------------------------------------------------------ */
	
	/* Manipulate methods ----------------------------------------- */
    public Milestone<T> addFirst(T step)
    {
        Milestone<T> ret = this;

        this.theFirstLeaf = new Leaf<T>(null, step);

        if (this.theFirstLeaf != null)
        {
            this.milestones = new ArrayList<T>();
            this.milestones.add(this.theFirstLeaf.getStep());

            this.steps = new ArrayList<T>();
            this.steps.add(step);

            this.index = -1;
        }

        return ret;
    }

	
	/* ------------------------------------------------------------ */
	
    
    
    
    /* Navigation methods ----------------------------------------- */
    /* ------------------------------------------------------------ */
	
	
	
	
	
	
	
	
	
	@Override
	public Milestone addChild(T parent, T child) {
        Milestone<T> ret = null;

        if (this.theFirstLeaf == null || parent == null) return ret;

        Leaf<T> leaf = this.find(theFirstLeaf, parent);
        if (leaf != null)
        {
            Leaf<T> s = new Leaf<T>(leaf, child);
            if (s != null)
            {
                leaf.getChildren().add(s);

                if (this.steps.stream().filter(A -> A.getID()==child.getID()).findFirst() == null)
                    this.steps.add(child);

                ret = this;
            }
        }

        return ret;
	}

    @Override
	public T next() {
    	return this.Next(null);
	}

	
    public Leaf<T> find(Leaf<T> currentLeaf, T step)
    {
        Leaf<T> ret = null;

        if (this.theFirstLeaf == null || currentLeaf == null) return null;

        if (currentLeaf.getStep().getID() == step.getID())
        {
            ret = currentLeaf;
        }
        else
        {
            for (Leaf<T> item : currentLeaf.getChildren())
            {
                Leaf<T> s = find(item, step);
                if (s != null)
                {
                    ret = s;
                    break;
                }
            }
        }

        return ret;
    }



    
    
    private T Next(T step)
    {
        T ret = null;

        if (this.theFirstLeaf == null) return ret;

        if (this.milestones == null || this.milestones.size() <= 0) return ret;

        // Find next step
        Leaf<T> leaf = null;
        if (step == null)
        {
            if (this.getCurrentLeaf() != null && this.getCurrentLeaf().getChildren() != null && this.getCurrentLeaf().getChildren().size() > 0)
            {
                leaf = this.getCurrentLeaf().getChildren().get(0);
            }
        }
        else if (this.getCurrentLeaf().getStep().getID() == step.getID())
        {
            leaf = this.getCurrentLeaf();
        }
        else
        {
            for (int i = 0; this.getCurrentLeaf() != null && this.getCurrentLeaf().getChildren() != null && i < this.getCurrentLeaf().getChildren().size(); i++)
            {
                if (this.getCurrentLeaf().getChildren().get(i).getStep().getID() == step.getID())
                {
                    leaf = this.getCurrentLeaf().getChildren().get(i);

                    break;
                }
            }
        }

        // if found, reload milestone & set index
        if (leaf != null && ReloadMilestone(leaf.getStep()))
        {
            ret = leaf.getStep();

            this.index = this.milestones.indexOf(leaf.getStep());
        }

        return ret;
    }


	
	

	
	
	
	
	
	
	
	
	
	
	
	

    public int getIndex() { 
    	 return this.index;
    }
    
    public void setIndex(int index) {
    	if (index >= 0 && index < this.milestones.size()) this.index = index;
    }
    
    public T getCurrentStep() { 
    	return this.milestones.get(this.index); 
    }


    public boolean RemoveChild(T step)
    {
        if (this.theFirstLeaf == null) return false;

        if (this.theFirstLeaf.getStep().getID() == step.getID())
        {
            this.theFirstLeaf = null;
            this.index = -1;

            return true;
        }

        boolean result = false;
        Leaf<T> leaf = find(this.theFirstLeaf, step);
        if (leaf != null)
        {
            Leaf<T> parent = leaf.getParent();
            parent.getChildren().remove(leaf);

            ReloadMilestone(parent.getStep());
            this.index = this.milestones.indexOf(parent.getStep());

            result = true;
        }

        return result;
    }

    public T GoTop()
    {
        T ret = null;

        if (this.theFirstLeaf == null) return ret;

        boolean result = this.ReloadMilestone(this.theFirstLeaf.getStep());
        if (result)
        {
            this.index = 0;

            ret = getCurrentLeaf().getStep();
        }

        return ret;
    }

    public T Go(T step)
    {
        T ret = null;

        boolean result = this.ReloadMilestone(step);
        if (result)
        {
            this.index = this.milestones.indexOf(step);

            ret = this.getCurrentLeaf().getStep();
        }

        return ret;
    }

    public T Prev()
    {
        if (this.theFirstLeaf == null) return null;

        if (this.index >= 0) this.index--;

        return (this.getCurrentLeaf() != null) ? this.getCurrentLeaf().getStep() : null;
    }

    public boolean ActiveCurrent()
    {
        boolean ret = false;

        if (this.index >= 0 && this.index < this.milestones.size())
        {
            //Deactive(CurrentLeaf);
            getCurrentLeaf().getStep().setActive(true);
        }

        return ret;
    }

    public boolean DeactiveCurrent()
    {
        boolean ret = false;

        if (this.index >= 0 && this.index < this.milestones.size())
        {
            Deactive(getCurrentLeaf());
            ret = true;
        }

        return ret;
    }

    public boolean ReloadMilestone(T step)
    {
        boolean result = false;

        // Find step by name
        Leaf<T> s = find(this.theFirstLeaf, step);
        if (s == null) return result;

        // Find this branch's last one of someone step by step
        Leaf<T> last = GetLastStep(s);
        if (last == null) return result;

        // Generate milestone
        List<T> milestone = new ArrayList<T>();
        result = getMilestone(last, milestone);
        if (result && (milestone != null && milestone.size() > 0))
        {
            this.milestones = new ArrayList<T>();
            for (int i = milestone.size() - 1; i >= 0; i--)
            {
                this.milestones.add(milestone.get(i));
            }
        }

        return result;
    }

    public T GetPrev()
    {
        if (this.milestones == null || this.milestones.Count <= 0) return null;

        int i = 0;
        if (this.index > 0)
            i = this.index - 1;

        return this.milestones.get(i);
    }

    public T GetNext()
    {
        if (this.milestones == null || this.milestones.size() <= 0) return null;

        int i = 0;
        i = this.index + 1;

        if (i >= this.milestones.size())
            return null;

        return this.milestones.get(i);
    }

    /*
    public virtual byte[] Serialize()
    {
        byte[] binaryMilestone = null;
        try
        {
            using (MemoryStream mStream = new MemoryStream())
            {
                BinaryFormatter bFormatter = new BinaryFormatter();
                bFormatter.Serialize(mStream, this);
                binaryMilestone = mStream.ToArray();
            }
        }
        catch (Exception ex)
        {
        }

        return binaryMilestone;
    }

    public virtual IMilestone<T> Deserialize(byte[] binary)
    {
        IMilestone<T> result = null;

        if (binary != null && binary.Length > 0)
        {
            try
            {
                using (MemoryStream mStream = new MemoryStream())
                {
                    mStream.Write(binary, 0, binary.Length);

                    BinaryFormatter bFormatter = new BinaryFormatter();
                    mStream.Position = 0;
                    result = (IMilestone<T>)bFormatter.Deserialize(mStream);
                }
            }
            catch (Exception ex)
            {
                result = null;
            }
        }

        return result;
    }
    */
    
    /* Functions */
    /* ----------------------------------------------------- */
    protected boolean Deactive(Leaf<T> leaf)
    {
        if (this.milestones == null || leaf == null) return false;

        leaf.getStep().setActive(false);

        for (int i = 0; leaf.getChildren() != null && i < leaf.getChildren().size(); i++)
        {
            Deactive(leaf.getChildren().get(i));
        }

        return true;
    }

    protected Leaf<T> GetLastStep(Leaf<T> midLeaf)
    {
        if (this.milestones == null || midLeaf == null) return null;

        Leaf<T> result = null;

        if (midLeaf.getChildren() == null || midLeaf.getChildren().size() <= 0)
        {
            result = midLeaf;
        }
        else
        {
            for (int i = 0; i < midLeaf.getChildren().size(); i++)
            {
                Leaf<T> s = GetLastStep(midLeaf.getChildren().get(i));
                if (s != null)
                {
                    result = s;
                    break;
                }
            }
        }

        return result;
    }

    protected boolean GetMilestone(Leaf<T> leaf, List<T> milestone)
    {
        if (this.milestones == null || leaf == null) return false;

        milestone.add(leaf.getStep());

        if (leaf.getParent() != null)
        {
            return GetMilestone(leaf.getParent(), milestone);
        }

        return true;
    }
/*
    public class HomeController : Controller
    {
        /// Milestone: Step00 --- Step01 --- Step02 --- Step03
        /// Milestone: Step00 --- Step04 --- Step06 --- Step07
        /// ----------------------------------------------------------------
        /// Steps tree: Step00 -+- Step01 --- Step02 --- Step03
        ///                     +- Step04 -+- Step05
        ///                     |          +- Step06 --- Step07
        ///                     +- Step08 --- Step09
        public ActionResult Index()
        {
            ViewBag.Message = "Modify this template to kick-start your ASP.NET MVC application.";
            Models.MilestoneModel retModel = new Models.MilestoneModel();

            MilestoneWeb milestone = eCeepMilestone.CreateMilestoneWeb();

            //Step ss = Step.Create(null, "", "", "");
            /*
                public static EnumMilestone Step00 { get { return new EnumMilestone("Step00", "Step0 path", "Step0 title"); } }
                public static EnumMilestone Step01 { get { return new EnumMilestone("Step01", "Step1 path", "Step1 title"); } }
                public static EnumMilestone Step02 { get { return new EnumMilestone("Step02", "Step2 path", "Step2 title"); } }
                public static EnumMilestone Step03 { get { return new EnumMilestone("Step03", "Step3 path", "Step3 title"); } }
                public static EnumMilestone Step04 { get { return new EnumMilestone("Step04", "Step4 path", "Step4 title"); } }
                public static EnumMilestone Step05 { get { return new EnumMilestone("Step05", "Step5 path", "Step5 title"); } }
                public static EnumMilestone Step06 { get { return new EnumMilestone("Step06", "Step6 path", "Step6 title"); } }
                public static EnumMilestone Step07 { get { return new EnumMilestone("Step07", "Step7 path", "Step7 title"); } }
                public static EnumMilestone Step08 { get { return new EnumMilestone("Step08", "Step8 path", "Step8 title"); } }
                public static EnumMilestone Step09 { get { return new EnumMilestone("Step09", "Step9 path", "Step9 title"); } }
             */

            milestone.AddFirst(EnumStep.Step00);

            milestone.AddChild(EnumStep.Step00,EnumStep.Step01)
                     .AddChild(EnumStep.Step01, EnumStep.Step02)
                     .AddChild(EnumStep.Step02, EnumStep.Step03);

            milestone.AddChild(EnumStep.Step00, EnumStep.Step04)
                     .AddChild(EnumStep.Step04, EnumStep.Step05);

            milestone.AddChild(EnumStep.Step04, EnumStep.Step06)
                     .AddChild(EnumStep.Step06, EnumStep.Step07);

            milestone.AddChild(EnumStep.Step00, EnumStep.Step08)
                     .AddChild(EnumStep.Step08, EnumStep.Step09);

            milestone.GoTop();

            //IList<string> dd = milestone.GetMile("Step01");
            //dd = milestone.GetMile("Step06");
            //dd = milestone.GetMile("Step03");

            eCeep.Milestone.EnumWebStep ret = null;
            IList<string> outSteps = new List<string>();

            ret = milestone.Next(EnumStep.Step00);
            outSteps.Add(String.Format("<tr><td>Next(\"Step0{0}\", \"Step{0} path\", \"Step{0} title\")</td><td>==> {1}</td></tr>", "0", ret.Uri));

            ret = milestone.Next(EnumStep.Step01);
            outSteps.Add(String.Format("<tr><td>Next(\"Step0{0}\", \"Step{0} path\", \"Step{0} title\")</td><td>==> {1}</td></tr>", "1", ret.Uri));

            ret = milestone.Next(EnumStep.Step02);
            outSteps.Add(String.Format("<tr><td>Next(\"Step0{0}\", \"Step{0} path\", \"Step{0} title\")</td><td>==> {1}</td></tr>", "2", ret.Uri));

            ret = milestone.Next(EnumStep.Step03);
            outSteps.Add(String.Format("<tr><td>Next(\"Step0{0}\", \"Step{0} path\", \"Step{0} title\")</td><td>==> {1}</td></tr>", "3", ret.Uri));

            ret = milestone.Prev();
            outSteps.Add(String.Format("<tr><td>Prev()</td><td>==> {0}</td></tr>", ret.Uri));

            ret = milestone.Prev();
            outSteps.Add(String.Format("<tr><td>Prev()</td><td>==> {0}</td></tr>", ret.Uri));

            ret = milestone.Prev();
            outSteps.Add(String.Format("<tr><td>Prev()</td><td>==> {0}</td></tr>", ret.Uri));

            ret = milestone.Next(EnumStep.Step04);
            outSteps.Add(String.Format("<tr><td>Next(\"Step0{0}\", \"Step{0} path\", \"Step{0} title\")</td><td>==> {1}</td></tr>", "4", ret.Uri));

            ret = milestone.Next(EnumStep.Step05);
            outSteps.Add(String.Format("<tr><td>Next(\"Step0{0}\", \"Step{0} path\", \"Step{0} title\")</td><td>==> {1}</td></tr>", "5", ret.Uri));

            //milestone.AddChild("Step04", "Step06", "Step6 path", "Step6 title");
            //milestone.AddChild("Step06", "Step07", "Step7 path", "Step7 title");

            ret = milestone.Prev();
            outSteps.Add(String.Format("<tr><td>Prev()</td><td>==> {0}</td></tr>", ret.Uri));

            ret = milestone.Prev();
            outSteps.Add(String.Format("<tr><td>Prev()</td><td>==> {0}</td></tr>", ret.Uri));

            ret = milestone.Next(EnumStep.Step01);
            outSteps.Add(String.Format("<tr><td>Step0{0}</td><td>==> {1}</td></tr>", "1", ret.Uri));

            ret = milestone.Next(EnumStep.Step02);
            outSteps.Add(String.Format("<tr><td>Step0{0}</td><td>==> {1}</td></tr>", "2", ret.Uri));

            ret = milestone.Next(EnumStep.Step03);
            outSteps.Add(String.Format("<tr><td>Step0{0}</td><td>==> {1}</td></tr>", "3", ret.Uri));

            //milestone.AddChild("Step08", "Step08", "Step8 path", "Step8 title");
            //milestone.AddChild("Step09", "Step09", "Step9 path", "Step9 title");

            //Step00 --- Step01 --- Step02 --- Step03
            ret = milestone.Prev();
            outSteps.Add(String.Format("<tr><td>Prev()</td><td>==> {0}</td></tr>", ret.Uri));

            ret = milestone.Prev();
            outSteps.Add(String.Format("<tr><td>Prev()</td><td>==> {0}</td></tr>", ret.Uri));

            ret = milestone.Prev();
            outSteps.Add(String.Format("<tr><td>Prev()</td><td>==> {0}</td></tr>", ret.Uri));

            //Step00 --- Step04 --- Step06 --- Step07
            milestone.Index = 0;
            milestone.Next(EnumStep.Step04);
            milestone.ActiveCurrent();
            //milestone.Next("Step06");
            //milestone.ActiveCurrent();
            //milestone.Next("Step07");

            //milestone.GoTop();
            milestone.Go(EnumStep.Step02);

            milestone.Go(EnumStep.Step06);

            milestone.RemoveChild(EnumStep.Step06);

            //byte[] data = milestone.Serialize();
            //eCeep.Milestone.IMilestoneWeb mile = milestone.Deserialize(data);

            //milestone = mile;

            //Display steps
            retModel.Steps = "<table style='width:800px;border:solid 1px black;'>";
            foreach (var item in outSteps)
            {
                retModel.Steps += item;
            }
            retModel.Steps += "</table>";

            //Display milestone
            retModel.Milestone = milestone.GetMilestoneHyperlinks("");

            return View(retModel);
        }

        public ActionResult About()
        {
            ViewBag.Message = "Your app description page.";

            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";

            return View();
        }
    }
	
 */
	
}
